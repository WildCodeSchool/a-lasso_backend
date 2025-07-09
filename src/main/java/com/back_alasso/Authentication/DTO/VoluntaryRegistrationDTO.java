package com.back_alasso.Authentication.DTO;

import static com.back_alasso.Authentication.ValidationConstants.*;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record VoluntaryRegistrationDTO(
  @NotBlank(message = "Le prénom est requis")
  @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "Le prénom doit contenir entre 2 et 50 caractères")
  String first_name,

  @NotBlank(message = "Le nom est requis")
  @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "Le nom doit contenir entre 2 et 50 caractères")
  String last_name,

  @NotBlank(message = "L'email est requis") @Email(message = "L'email doit être valide") String email,

  @NotBlank(message = "Le mot de passe est requis")
  @Pattern(
    regexp = PASSWORD_REGEX,
    message = "Le mot de passe doit contenir une minuscule, une majuscule, un chiffre, un caractère spécial et au moins 8 caractères"
  )
  String password,

  @Pattern(regexp = PHONE_REGEX, message = "Le numéro de téléphone est invalide") String mobile_phone,

  @NotBlank(message = "La ville est requise")
  @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "La ville doit contenir entre 2 et 50 caractères")
  String city,

  @NotBlank(message = "Le pays est requis")
  @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "Le pays doit contenir entre 2 et 50 caractères")
  String country,

  @Past(message = "La date de naissance doit être dans le passé") @NotNull(message = "La date de naissance est requise") LocalDate birth_date
) {}
