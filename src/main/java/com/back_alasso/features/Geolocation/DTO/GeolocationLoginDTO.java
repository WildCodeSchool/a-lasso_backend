package com.back_alasso.features.Geolocation.DTO;

import com.back_alasso.features.Geolocation.Geolocation;

public record GeolocationLoginDTO(double longitude, double latitude) {
  public static GeolocationLoginDTO from(Geolocation geolocation) {
    return new GeolocationLoginDTO(geolocation.getLongitude(), geolocation.getLatitude());
  }
}
