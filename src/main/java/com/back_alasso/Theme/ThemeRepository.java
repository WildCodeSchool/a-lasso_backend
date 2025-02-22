package com.back_alasso.Theme;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ThemeRepository extends JpaRepository<Theme, UUID> {
    Theme findByName(ThemeNameEnumType name);

}
