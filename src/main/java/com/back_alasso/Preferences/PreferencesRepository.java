package com.back_alasso.Preferences;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferencesRepository extends JpaRepository<Preferences, UUID> {}
