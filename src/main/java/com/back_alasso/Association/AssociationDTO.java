package com.back_alasso.Association;

import com.back_alasso.Geolocalisation.GeolocalisationDTO;
import java.util.stream.Collectors;

public record AssociationDTO(String name, String logo, boolean isFollow, GeolocalisationDTO localisation) {
  public static AssociationDTO getAssociationDTO(Association association) {
    return new AssociationDTO(
      association.getName(),
      association
        .getAssociationImages()
        .stream()
        .filter(i -> i.getImage().getType().name().equals("LOGO"))
        .map(i -> i.getImage().getUrl())
        .collect(Collectors.joining()),
      association.getAssociationFollowers().stream().anyMatch(a -> a.getAssociation().getId().equals(association.getId()) && a.isIs_follow()),
      GeolocalisationDTO.getCoordinates(association)
    );
  }
}
