package com.back_alasso.features.Geolocation;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeolocationRepository extends JpaRepository<Geolocation, UUID> {}
