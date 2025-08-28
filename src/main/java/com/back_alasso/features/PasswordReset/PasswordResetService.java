package com.back_alasso.features.PasswordReset;

import com.back_alasso.core.MailService;
import com.back_alasso.features.User.User;
import com.back_alasso.features.User.UserRepository;
import com.back_alasso.security.JwtService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

  private static final int NUMBER_FIFTEEN = 15;
  private final JwtService jwtService;
  private final PasswordResetRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final MailService mailService;

  @Value("${custom.client-url}")
  private String clientUrl;

  public PasswordResetService(
    JwtService jwtService,
    PasswordResetRepository tokenRepository,
    PasswordEncoder passwordEncoder,
    UserRepository userRepository,
    MailService mailService
  ) {
    this.jwtService = jwtService;
    this.tokenRepository = tokenRepository;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.mailService = mailService;
  }

  @Transactional
  public void sendPasswordResetEmail(User user) {
    tokenRepository.deleteByUser(user);
    String token = UUID.randomUUID().toString();

    PasswordReset resetToken = new PasswordReset();
    resetToken.setToken(token);
    resetToken.setUser(user);
    resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(NUMBER_FIFTEEN));
    tokenRepository.save(resetToken);

    String resetLink = clientUrl + "/reset-password?token=" + token;

    mailService.buildMail(
      user.getEmail(),
      "Réinitialisation du mot de passe",
      "Cliquez sur ce lien pour réinitialiser votre mot de passe : " + resetLink
    );
  }

  @Transactional
  public void resetPassword(String token, String newPassword) {
    PasswordReset resetToken = tokenRepository.findByToken(token).orElseThrow(() -> new IllegalArgumentException("Token de reset password invalide"));

    if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("Token de reset password expiré");
    }

    User user = resetToken.getUser();

    user.setHashed_password(passwordEncoder.encode(newPassword));
    userRepository.save(user);

    tokenRepository.delete(resetToken);
  }
}
