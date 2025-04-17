package com.back_alasso.Theme;

import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/themes")
public class ThemeController {

  private final ThemeService themeService;

  public ThemeController(ThemeService themeService) {
    this.themeService = themeService;
  }

  @GetMapping
  public ResponseEntity<Set<ThemeDTO>> getAllThemes() {
    Set<ThemeDTO> themes = themeService.getAllActivitiesThemes();
    return ResponseEntity.status(HttpStatus.OK).body(themes);
  }
}
