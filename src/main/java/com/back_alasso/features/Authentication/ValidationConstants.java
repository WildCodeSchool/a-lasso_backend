package com.back_alasso.features.Authentication;

public class ValidationConstants {

  public static final int MIN_LENGTH = 2;
  public static final int MAX_LENGTH = 50;
  public static final int MIN_PASSWORD_LENGTH = 8;
  public static final String SIRET_REGEX = "^\\d{14}$";
  public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";
  public static final String PHONE_REGEX = "^(\\+33|0)[1-9](\\d{2}){4}$";
}
