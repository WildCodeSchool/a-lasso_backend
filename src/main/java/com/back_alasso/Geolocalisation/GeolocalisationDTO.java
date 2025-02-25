package com.back_alasso.Geolocalisation;

public record GeolocalisationDTO(String city, double longitude, double latitude) {
  public static GeolocalisationDTO getCoordinates(Geolocatable entity) {
    return new GeolocalisationDTO(
      entity.getAddress().getCity(),
      entity.getGeolocalisation().getLongitude(),
      entity.getGeolocalisation().getLatitude()
    );
  }
}
