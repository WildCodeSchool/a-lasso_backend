package com.back_alasso.features.Activity.DTO;

import com.back_alasso.features.ActivityVoluntary.DTO.ActivityParticipantsRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UpdateRegisteredResponseDTO(
  @NotNull(message = "Le nouvel état doit être renseigné") Boolean isRegistered,
  @NotNull(message = "Le nombre de participants est nécessaire") @Valid ActivityParticipantsRequestDTO activityParticipantsRequestDTO
) {}
