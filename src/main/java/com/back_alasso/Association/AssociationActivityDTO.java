package com.back_alasso.Association;

import com.back_alasso.Geolocation.GeolocationDTO;
import java.util.UUID;
import java.util.stream.Collectors;

public record AssociationActivityDTO(UUID id, String name, String logo, boolean isFollow, GeolocationDTO localisation) {
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
      association.getAssociationFollowers().stream().anyMatch(a -> a.getAssociation().getId().equals(association.getId()) && a.isFollow()),
      GeolocationDTO.getCoordinates(association)
    );
  }
}
