package com.back_alasso.features.Geolocation.DTO;

import java.util.List;

public record GeolocApiGouvResponseDTO(List<Feature> features) {
  public record Feature(Geometry geometry, Properties properties) {}

  public record Geometry(List<Double> coordinates) {}

  public record Properties(String label, String housenumber, String street, String postcode, String city, String context) {}
}
