package com.back_alasso.features.Association.DTO;

import jakarta.validation.constraints.NotNull;

public record UpdateFollowRequestDTO(@NotNull(message = "Le status de suivi doit être renseigné") Boolean isFollow) {}
