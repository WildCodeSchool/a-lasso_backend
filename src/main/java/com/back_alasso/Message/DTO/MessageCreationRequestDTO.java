package com.back_alasso.Message.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record MessageCreationRequestDTO(
  @NotNull(message = "L'id de l'activity ne peux pas être null") UUID activityId,
  @NotBlank(message = "Le contenu du message de l'activity ne peux pas être null") String content,
  @NotNull(message = "La date du message ne peut pas être null") LocalDateTime date
) {}
