package com.back_alasso.features.Voluntary.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record VoluntaryUpdateRequestDTO(
  @NotBlank(message = "Le prénom doit être renseigné") String first_name,
  @NotBlank(message = "Le nom doit être renseigné") String last_name,
  @NotNull(message = "La date de naissance doit être renseigné") LocalDate birth_date,
  @NotBlank(message = "La ville doit être renseigné") String city,
  @NotBlank(message = "Le pays doit être renseigné") String country,
  @NotBlank(message = "Le téléphone doit être renseigné") String mobile_phone
) {}
