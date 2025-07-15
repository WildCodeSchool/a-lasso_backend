package com.back_alasso.Geolocation.DTO;

import com.back_alasso.Geolocation.Geolocatable;
import jakarta.validation.constraints.NotNull;

public record GeolocationRequestDTO(
  @NotNull(message = "La longitude doit être renseigné") double longitude,
  @NotNull(message = "La latitude doit être renseigné") double latitude
) {
  public static GeolocationRequestDTO getCoordinates(Geolocatable entity) {
    double longitude = (entity.getGeolocation() != null) ? entity.getGeolocation().getLongitude() : 0.0;

    double latitude = (entity.getGeolocation() != null) ? entity.getGeolocation().getLatitude() : 0.0;

    return new GeolocationRequestDTO(longitude, latitude);
  }
}
