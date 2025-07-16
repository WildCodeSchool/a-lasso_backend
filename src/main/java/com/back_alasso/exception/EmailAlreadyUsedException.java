package com.back_alasso.exception;

public class EmailAlreadyUsedException extends RuntimeException {

  public EmailAlreadyUsedException(String message) {
    super(message);
  }
}
