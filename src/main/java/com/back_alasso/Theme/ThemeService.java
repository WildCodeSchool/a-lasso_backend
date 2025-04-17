package com.back_alasso.Theme;

import com.back_alasso.Exception.ResourceNotFoundException;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

  private final ThemeRepository themeRepository;

  public ThemeService(ThemeRepository themeRepository) {
    this.themeRepository = themeRepository;
  }

  public Set<ThemeDTO> getAllActivitiesThemes() {
    List<Theme> themes = themeRepository.findAll();

    if (themes.isEmpty()) {
      throw new ResourceNotFoundException("Themes not found");
    }

    return themes
      .stream()
      .map(ThemeDTO::fromEntityToDTO)
      .sorted(Comparator.comparing(dto -> dto.name().name()))
      .collect(Collectors.toCollection(LinkedHashSet::new));
  }
}
