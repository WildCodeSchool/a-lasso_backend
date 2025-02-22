package com.back_alasso.Geolocalisation;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeolocalisationRepository extends JpaRepository<Geolocalisation, UUID> {
  Optional<Geolocalisation> findByLongitudeAndLatitude(double longitude, double latitude);
}
