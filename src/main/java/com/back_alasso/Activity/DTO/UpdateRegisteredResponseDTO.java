package com.back_alasso.Activity.DTO;

import com.back_alasso.ActivityVoluntary.DTO.ActivityParticipantsRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UpdateRegisteredResponseDTO(
  @NotNull(message = "Le nouvel état doit être renseigné") Boolean isRegistered,
  @NotNull(message = "Le nombre de participants est nécessaire") @Valid ActivityParticipantsRequestDTO activityParticipantsRequestDTO
) {}
