package com.back_alasso.Geolocation;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeolocationRepository extends JpaRepository<Geolocation, UUID> {
  Optional<Geolocation> findByLongitudeAndLatitude(double longitude, double latitude);
}
