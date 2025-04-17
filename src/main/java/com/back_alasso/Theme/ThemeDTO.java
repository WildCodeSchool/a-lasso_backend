package com.back_alasso.Theme;

public record ThemeDTO(ThemeNameEnumType name, String iconUrl) {
  public static ThemeDTO fromEntityToDTO(Theme theme) {
    return new ThemeDTO(theme.getName(), theme.getIcon_url());
  }
}
