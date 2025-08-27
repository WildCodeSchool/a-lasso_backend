package com.back_alasso.features.Theme;

import com.back_alasso.exception.ResourceNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {

  private final ThemeRepository themeRepository;

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

  public List<Theme> getThemes(List<ThemeNameEnumType> themeNames) {
    if (themeNames == null || themeNames.isEmpty()) {
      return Collections.emptyList();
    }

    List<Theme> themes = themeRepository.findAllByNameIn(themeNames);
    if (themes.isEmpty()) {
      throw new ResourceNotFoundException("Themes not found");
    }
    return themes;
  }
}
