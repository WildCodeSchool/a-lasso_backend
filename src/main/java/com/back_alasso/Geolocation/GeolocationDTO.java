package com.back_alasso.Geolocation;

public record GeolocationDTO(double longitude, double latitude) {

    public static GeolocationDTO getCoordinates(Geolocatable entity) {
        double longitude = (entity.getGeolocation() != null) ? entity.getGeolocation().getLongitude() : 0.0;

        double latitude = (entity.getGeolocation() != null) ? entity.getGeolocation().getLatitude() : 0.0;

        return new GeolocationDTO(longitude, latitude);
    }
}
