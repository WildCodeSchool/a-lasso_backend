package com.back_alasso.security;

import java.util.*;

public class SecurityConstants {

  public static final Set<String> PUBLIC_URLS = new HashSet<>(Arrays.asList("/auth/**", "/activities", "/association/*", "/images/**"));

  public static final Set<String> PRIVATE_URLS = new HashSet<>(Arrays.asList("/association/*/updateFollow"));
}
