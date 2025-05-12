package com.back_alasso.security;

import java.util.*;

public class SecurityConstants {

  public static final Set<String> PUBLIC_URLS = new HashSet<>(
    Arrays.asList("/auth/**", "/themes", "/activities", "/activities/{id}", "/association/{id}", "/images/**")
  );

  public static final Set<String> ASSOCIATION_URLS = new HashSet<>(Arrays.asList("/activities/publish"));
}
