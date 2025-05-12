package com.back_alasso.Theme;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, UUID> {
  Theme findByName(ThemeNameEnumType name);

  List<Theme> findAllByNameIn(List<ThemeNameEnumType> names);
}
