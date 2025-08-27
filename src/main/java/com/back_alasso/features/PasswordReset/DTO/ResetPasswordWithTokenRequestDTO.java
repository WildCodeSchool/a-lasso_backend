package com.back_alasso.features.PasswordReset.DTO;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordWithTokenRequestDTO(@NotBlank String token, @NotBlank String newPassword) {}
