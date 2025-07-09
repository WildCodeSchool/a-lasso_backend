package com.back_alasso.Authentication.DTO;

import static com.back_alasso.Authentication.ValidationConstants.*;

import com.back_alasso.Address.AddressRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record AssociationRegistrationDTO(
  @NotBlank(message = "Le SIRET est requis") @Pattern(regexp = SIRET_REGEX, message = "Le SIRET doit contenir 14 chiffres") String siret,

  @NotBlank(message = "Le nom est requis")
  @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "Le nom doit contenir entre 2 et 50 caractères")
  String name,

  @NotBlank(message = "L'email est requis") @Email(message = "L'email doit être valide") String email,

  @NotBlank(message = "Le mot de passe est requis")
  @Pattern(
    regexp = PASSWORD_REGEX,
    message = "Le mot de passe doit contenir une minuscule, une majuscule, un chiffre, un caractère spécial et au moins 8 caractères"
  )
  String password,

  @Pattern(regexp = PHONE_REGEX, message = "Le numéro de téléphone est invalide") String mobile_phone,

  @NotNull(message = "L'adresse est requise") @Valid AddressRequestDTO address
) {}
