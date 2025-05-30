package com.back_alasso.Association.DTO;

import com.back_alasso.Association.Association;
import com.back_alasso.Geolocation.GeolocationDTO;
import java.util.UUID;
import java.util.stream.Collectors;

public record AssociationActivityDTO(UUID id, String name, String logo, GeolocationDTO localisation) {
  public static AssociationActivityDTO getAssociationDTO(Association association) {
    return new AssociationActivityDTO(
      association.getId(),
      association.getName(),
      association
        .getAssociationImages()
        .stream()
        .filter(i -> i.getImage().getType().name().equals("LOGO"))
        .map(i -> i.getImage().getUrl())
        .collect(Collectors.joining()),
      GeolocationDTO.getCoordinates(association)
    );
  }
}
