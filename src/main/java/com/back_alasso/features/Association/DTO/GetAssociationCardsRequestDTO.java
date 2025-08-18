package com.back_alasso.features.Association.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public record GetAssociationCardsRequestDTO(
  @NotNull(message = "La liste des IDs ne peut pas être null")
  @Size(min = 1, max = MAX_IDS, message = "Le nombre d'IDs doit être entre 1 et 50")
  List<UUID> ids
) {
  public static final int MAX_IDS = 50;
}
