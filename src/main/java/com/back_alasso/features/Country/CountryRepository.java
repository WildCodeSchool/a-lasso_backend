package com.back_alasso.features.Country;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, UUID> {
  Optional<Country> findFirstByName(String country);
}
