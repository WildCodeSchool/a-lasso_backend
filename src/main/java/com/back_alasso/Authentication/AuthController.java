package com.back_alasso.Authentication;

import com.back_alasso.Association.Association;
import com.back_alasso.Association.AssociationLoginDTO;
import com.back_alasso.Report.ReportRepository;
import com.back_alasso.Report.StatusReportEnumType;
import com.back_alasso.User.User;
import com.back_alasso.User.UserEnumType;
import com.back_alasso.User.UserService;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.Voluntary.VoluntaryLoginDTO;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;
  private final AuthService authService;
  private final ReportRepository reportRepository;

  public AuthController(UserService userService, AuthService authService, ReportRepository reportRepository) {
    this.userService = userService;
    this.authService = authService;
    this.reportRepository = reportRepository;
  }

  @PostMapping("/register/voluntary")
  public ResponseEntity<Boolean> register(@Valid @RequestBody VoluntaryRegistrationDTO voluntaryRegistrationDTO) {
    userService.checkUserExists(voluntaryRegistrationDTO.email());
    boolean isRegisteredSuccess = userService.registerVoluntary(voluntaryRegistrationDTO);

    return ResponseEntity.status(isRegisteredSuccess ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).body(isRegisteredSuccess);
  }

  @PostMapping("/register/association")
  public ResponseEntity<Boolean> register(@Valid @RequestBody AssociationRegistrationDTO associationRegistrationDTO) {
    userService.checkUserExists(associationRegistrationDTO.email());
    boolean isRegisteredSuccess = userService.registerAssociation(associationRegistrationDTO);

    return ResponseEntity.status(isRegisteredSuccess ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).body(isRegisteredSuccess);
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, Object>> authenticate(@Valid @RequestBody UserLoginDTO userLoginDTO) {
    String token = authService.authenticate(userLoginDTO.email(), userLoginDTO.password());
    User user = userService.findByEmail(userLoginDTO.email());

    Map<String, Object> response = new HashMap<>();
    response.put("token", token);

    if (user instanceof Voluntary voluntary) {
      Boolean isAdmin = voluntary.getRoles().contains(UserEnumType.ROLE_ADMIN);
      Integer reportsInProgress = isAdmin ? reportRepository.countByStatus(StatusReportEnumType.IN_PROGRESS) : null;

      response.put("user", VoluntaryLoginDTO.fromEntityToDTO(voluntary, reportsInProgress));
    } else if (user instanceof Association association) {
      response.put("user", AssociationLoginDTO.fromEntityToDTO(association));
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
