package com.back_alasso.features.Authentication;

import com.back_alasso.features.Activity.DTO.OnPublish;
import com.back_alasso.features.Association.Association;
import com.back_alasso.features.Association.AssociationLoginResponseMapper;
import com.back_alasso.features.Authentication.DTO.*;
import com.back_alasso.features.Authentication.DTO.VoluntaryRegistrationDTO;
import com.back_alasso.features.User.AccountEnumType;
import com.back_alasso.features.User.User;
import com.back_alasso.features.User.UserService;
import com.back_alasso.features.Voluntary.Voluntary;
import com.back_alasso.features.Voluntary.VoluntaryLoginResponseMapper;
import com.back_alasso.security.LoginRateLimitFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  @Autowired
  private Validator validator;

  private final UserService userService;
  private final AuthService authService;
  private final VoluntaryLoginResponseMapper voluntaryLoginResponseMapper;
  private final AssociationLoginResponseMapper associationLoginResponseMapper;
  private final LoginRateLimitFilter loginRateLimitFilter;

  @PostMapping("/register/voluntary")
  public ResponseEntity<Boolean> register(@Valid @RequestBody VoluntaryRegistrationDTO voluntaryRegistrationDTO) {
    userService.checkUserExists(voluntaryRegistrationDTO.email());
    boolean isRegisteredSuccess = userService.registerVoluntary(voluntaryRegistrationDTO);

    return ResponseEntity.status(isRegisteredSuccess ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).body(isRegisteredSuccess);
  }

  @PostMapping("/register/association")
  public ResponseEntity<Boolean> register(@RequestBody AssociationRegistrationDTO associationRegistrationDTO) {
    validator.validate(associationRegistrationDTO, OnPublish.class);

    userService.checkUserExists(associationRegistrationDTO.email());
    boolean isRegisteredSuccess = userService.registerAssociation(associationRegistrationDTO);

    return ResponseEntity.status(isRegisteredSuccess ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).body(isRegisteredSuccess);
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, Object>> authenticate(
    @Valid @RequestBody UserLoginDTO userLoginDTO,
    HttpServletRequest request,
    HttpServletResponse res
  ) {
    String key = "ip:" + (request.getHeader("X-Forwarded-For") != null ? request.getHeader("X-Forwarded-For") : request.getRemoteAddr());

    try {
      String token = authService.authenticate(userLoginDTO.email(), userLoginDTO.password());
      User user = userService.findByEmail(userLoginDTO.email());

      if (user.getAccount_status().equals(AccountEnumType.BANNED)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      }

      loginRateLimitFilter.resetBucket(key);
      res.setHeader("X-Rate-Limit-Remaining", String.valueOf(loginRateLimitFilter.getRemainingAttempt(key)));

      Map<String, Object> response = new HashMap<>();
      response.put("token", token);
      authService.addUserToResponse(user, response);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
    }
  }

  @PatchMapping("/change-password")
  public ResponseEntity<Map<String, Object>> changePassword(
    @Valid @RequestBody PasswordChangeRequestDTO dto,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    try {
      userService.changePassword(userDetails.getUsername(), dto.oldPassword(), dto.newPassword());
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
    }
  }

  @PatchMapping("/change-email")
  public ResponseEntity<Map<String, Object>> changeEmail(@RequestBody EmailChangeRequestDTO dto, @AuthenticationPrincipal UserDetails userDetails) {
    try {
      User updatedUser = userService.changeEmail(userDetails.getUsername(), dto.newEmail(), dto.password());
      String newToken = authService.generateToken(updatedUser);
      Map<String, Object> response = new HashMap<>();
      response.put("token", newToken);
      if (updatedUser instanceof Voluntary voluntary) {
        response.put("user", voluntaryLoginResponseMapper.fromEntityToDTO(voluntary));
      } else if (updatedUser instanceof Association association) {
        response.put("user", associationLoginResponseMapper.fromEntityToDTO(association));
      }
      return ResponseEntity.ok(response);
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
