package com.back_alasso.features.PasswordReset;

import com.back_alasso.features.User.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {
  Optional<PasswordReset> findByToken(String token);

  void deleteByUser(User user);
}
