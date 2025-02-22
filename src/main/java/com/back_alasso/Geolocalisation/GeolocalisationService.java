package com.back_alasso.Geolocalisation;

import com.back_alasso.Activity.Activity;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class GeolocalisationService {

  private final GeolocalisationRepository geolocalisationRepository;

  public GeolocalisationService(GeolocalisationRepository geolocalisationRepository) {
    this.geolocalisationRepository = geolocalisationRepository;
  }

  public Geolocalisation findOrCreateGeolocalisation(double longitude, double latitude, Activity activity) {
    Optional<Geolocalisation> existingLocation = geolocalisationRepository.findByLongitudeAndLatitude(longitude, latitude);

    if (existingLocation.isPresent()) {
      // if localisation already exists, we add the new activity at the list
      Geolocalisation geolocalisation = existingLocation.get();
      geolocalisation.addActivity(activity);
      return geolocalisationRepository.save(geolocalisation);
    } else {
      // if localisation doesn't exist we add activity to a new localisation
      Geolocalisation newGeolocalisation = new Geolocalisation(longitude, latitude);
      newGeolocalisation.addActivity(activity);
      return geolocalisationRepository.save(newGeolocalisation);
    }
  }
}
