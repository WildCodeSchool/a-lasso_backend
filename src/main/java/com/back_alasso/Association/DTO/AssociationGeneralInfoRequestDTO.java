package com.back_alasso.Association.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AssociationGeneralInfoRequestDTO(
  @NotBlank(message = "le nom du fondateur est obligatoire") String founder,
  @NotNull(message = "la date de création est obligatoire") LocalDate foundationDate
) {}
