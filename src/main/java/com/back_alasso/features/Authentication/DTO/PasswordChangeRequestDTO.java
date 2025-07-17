package com.back_alasso.features.Authentication.DTO;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeRequestDTO(
  @NotBlank(message = "L'ancien mot de passe ne peut pas être vide") String oldPassword,
  @NotBlank(message = "Le nouveau mot de passe ne peut pas être vide") String newPassword
) {}
