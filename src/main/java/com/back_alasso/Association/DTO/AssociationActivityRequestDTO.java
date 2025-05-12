package com.back_alasso.Association.DTO;

import com.back_alasso.Association.Association;
import com.back_alasso.AssociationImage.AssociationImage;
import com.back_alasso.Geolocation.DTO.GeolocationRequestDTO;
import com.back_alasso.Image.Image;
import com.back_alasso.Image.ImageEnumType;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public record AssociationActivityRequestDTO(UUID id, String name, String logo, GeolocationRequestDTO localisation) {
  public static AssociationActivityRequestDTO getAssociationDTO(Association association) {
    String logoString = null;

    List<AssociationImage> logos = association.getAssociationImages().stream().filter(ai -> ai.getImage().getType() == ImageEnumType.LOGO).toList();

    if (!logos.isEmpty()) {
      Image logoImage = logos.get(0).getImage();
      if (logoImage.getData() != null && logoImage.getData().length > 0) {
        String format = logoImage.getFormat() != null ? logoImage.getFormat() : "png";
        logoString = "data:image/" + format + ";base64," + Base64.getEncoder().encodeToString(logoImage.getData());
      } else if (logoImage.getUrl() != null) {
        logoString = logoImage.getUrl();
      }
    }

    return new AssociationActivityRequestDTO(
      association.getId(),
      association.getName(),
      logoString,
      GeolocationRequestDTO.getCoordinates(association)
    );
  }
}
