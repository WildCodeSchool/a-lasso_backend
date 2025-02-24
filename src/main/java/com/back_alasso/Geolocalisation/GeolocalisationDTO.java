package com.back_alasso.Geolocalisation;

import com.back_alasso.Activity.Activity;

public record GeolocalisationDTO(
        String city
//        double longitude,
//        double latitude
) {

    public static GeolocalisationDTO getActivityCoordinates(Activity activity) {
        return new GeolocalisationDTO(
                activity.getAddress().getCity()

        );
    }
}
