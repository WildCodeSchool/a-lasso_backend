package com.back_alasso.Theme;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Theme {

  public static final int NAME_MAX_LENGTH = 12;
  public static final int ICON_URL_MAX_LENGTH = 255;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = NAME_MAX_LENGTH)
  private ThemeNameEnumType name;

  @Column(nullable = false, length = ICON_URL_MAX_LENGTH)
  private String icon_url;
}
