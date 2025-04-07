package com.back_alasso.security;

import java.util.*;

public class SecurityConstants {

  public static final Set<String> PUBLIC_URLS = new HashSet<>(
    Arrays.asList("/auth/**", "/activities", "/activities/{id}", "/association/{id}", "/images/**")
  );
}
