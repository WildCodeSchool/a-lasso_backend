package com.back_alasso.Geolocation;

public record GeolocationDTO(String city, double longitude, double latitude) {
  public static GeolocationDTO getCoordinates(Geolocatable entity) {
    return new GeolocationDTO(entity.getAddress().getCity(), entity.getGeolocation().getLongitude(), entity.getGeolocation().getLatitude());
  }
}
