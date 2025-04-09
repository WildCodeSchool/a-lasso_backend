package com.back_alasso.Association;

import com.back_alasso.Address.Address;
import com.back_alasso.Geolocation.GeolocationLoginDTO;
import com.back_alasso.Image.ImageEnumType;
import java.time.LocalDate;

public record AssociationLoginDTO(
  String email,
  String name,
  String description,
  String founder,
  LocalDate foundationDate,
  String siteURL,
  Address address,
  String associationProfileImageURL,
  String associationLogoImage,
  GeolocationLoginDTO geolocation
) {
  public static AssociationLoginDTO fromEntityToDTO(Association association) {
    return new AssociationLoginDTO(
      association.getEmail(),
      association.getName(),
      association.getDescription(),
      association.getFounder(),
      association.getFoundationDate(),
      association.getSiteURL(),
      association.getAddress(),
      association
        .getAssociationImages()
        .stream()
        .filter(associationImage -> associationImage.getImage().getType() == ImageEnumType.PROFILE_ASSOCIATION)
        .map(associationImage -> associationImage.getImage().getUrl())
        .findFirst()
        .orElse("defaultAssociationProfileImage.png"),
      association
        .getAssociationImages()
        .stream()
        .filter(associationImage -> associationImage.getImage().getType() == ImageEnumType.LOGO)
        .map(associationImage -> associationImage.getImage().getUrl())
        .findFirst()
        .orElse("defaultAssociationProfileImage.png"),
      GeolocationLoginDTO.from(association.getGeolocation())
    );
  }
}
