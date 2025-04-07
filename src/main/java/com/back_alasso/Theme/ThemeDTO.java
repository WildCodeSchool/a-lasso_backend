package com.back_alasso.Theme;

public record ThemeDTO(ThemeNameEnumType name, String icon_url) {
  public static ThemeDTO fromEntityToDTO(Theme theme) {
    return new ThemeDTO(theme.getName(), theme.getIcon_url());
  }
}
