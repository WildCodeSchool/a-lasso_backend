package com.back_alasso.security;

import java.util.*;

public class SecurityConstants {

  public static final Set<String> PUBLIC_URLS = new HashSet<>(
    Arrays.asList("/auth/**", "/activities", "/activities/{id}", "/association/{id}", "/images/**")
  );

  public static final Set<String> PRIVATE_URLS = new HashSet<>(Arrays.asList("/association/*/updateFollow", "/activities/*/updateFavorite"));
}
