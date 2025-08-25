package com.back_alasso.exception;

public class GeolocationNotFoundException extends RuntimeException {

  public GeolocationNotFoundException(String message) {
    super(message);
  }

  public GeolocationNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
