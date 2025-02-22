package com.back_alasso.Geolocalisation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GeolocalisationRepository extends JpaRepository<Geolocalisation, UUID> {
    Optional<Geolocalisation> findByLongitudeAndLatitude(double longitude, double latitude);
}
