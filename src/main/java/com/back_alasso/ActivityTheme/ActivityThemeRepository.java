package com.back_alasso.ActivityTheme;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActivityThemeRepository extends JpaRepository<ActivityTheme, UUID> {
}
