package com.back_alasso.features.Association.DTO;

import jakarta.validation.constraints.NotBlank;

public record AssociationDescriptionRequestDTO(@NotBlank(message = "la description est obligatoire") String description) {}
