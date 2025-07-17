package com.back_alasso.features.Address.DTO;

import com.back_alasso.features.Activity.DTO.OnPublish;
import jakarta.validation.constraints.NotBlank;

public record AddressRequestDTO(
  String houseNumber,

  @NotBlank(groups = { OnPublish.class }, message = "Le nom de rue est obligatoire") String streetName,

  @NotBlank(groups = { OnPublish.class }, message = "Le code postal est obligatoire") String zipCode,

  @NotBlank(groups = { OnPublish.class }, message = "La ville est obligatoire") String city,

  @NotBlank(groups = { OnPublish.class }, message = "Le pays est obligatoire") String country,

  @NotBlank(groups = { OnPublish.class }, message = "Le nom d'affichage est obligatoire") String displayName
) {}
