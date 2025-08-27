package com.back_alasso.PasswordReset;

import com.back_alasso.PasswordReset.DTO.PasswordResetRequestDTO;
import com.back_alasso.PasswordReset.DTO.ResetPasswordWithTokenRequestDTO;
import com.back_alasso.features.User.User;
import com.back_alasso.features.User.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reset-password")
public class PasswordResetController {

  private final PasswordResetService passwordResetService;
  private final UserService userService;

  public PasswordResetController(PasswordResetService passwordResetService, UserService userService) {
    this.passwordResetService = passwordResetService;
    this.userService = userService;
  }

  @PostMapping("/request")
  public ResponseEntity<Void> requestReset(@Valid @RequestBody PasswordResetRequestDTO request) {
    User user = userService.findByEmail(request.email());

    passwordResetService.sendPasswordResetEmail(user);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/confirm")
  public ResponseEntity<Boolean> confirmReset(@Valid @RequestBody ResetPasswordWithTokenRequestDTO request) {
    passwordResetService.resetPassword(request.token(), request.newPassword());
    return ResponseEntity.status(HttpStatus.OK).body(true);
  }
}
