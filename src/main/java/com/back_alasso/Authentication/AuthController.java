package com.back_alasso.Authentication;

import com.back_alasso.Association.Association;
import com.back_alasso.Association.AssociationLoginResponseMapper;
import com.back_alasso.User.User;
import com.back_alasso.User.UserService;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.Voluntary.VoluntaryLoginResponseMapper;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;
  private final AuthService authService;
  private final VoluntaryLoginResponseMapper voluntaryLoginResponseMapper;
  private final AssociationLoginResponseMapper associationLoginResponseMapper;

  public AuthController(
    UserService userService,
    AuthService authService,
    VoluntaryLoginResponseMapper voluntaryLoginResponseMapper,
    AssociationLoginResponseMapper associationLoginResponseMapper
  ) {
    this.userService = userService;
    this.authService = authService;
    this.voluntaryLoginResponseMapper = voluntaryLoginResponseMapper;
    this.associationLoginResponseMapper = associationLoginResponseMapper;
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
      response.put("user", voluntaryLoginResponseMapper.fromEntityToDTO(voluntary));
    } else if (user instanceof Association association) {
      response.put("user", associationLoginResponseMapper.fromEntityToDTO(association));
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PatchMapping("/change-password")
  public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO dto, @AuthenticationPrincipal UserDetails userDetails) {
    try {
      userService.changePassword(userDetails.getUsername(), dto.getOldPassword(), dto.getNewPassword());
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
    }
  }

  @DeleteMapping("/delete-account")
  public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
    userService.deleteUser(userDetails.getUsername());
    return ResponseEntity.noContent().build();
  }
}
