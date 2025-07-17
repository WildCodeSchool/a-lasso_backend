package com.back_alasso.features.Authentication.DTO;

import com.back_alasso.features.Voluntary.DTO.VoluntaryLoginResponseDTO;

public class LoginVoluntaryResult {

  private VoluntaryLoginResponseDTO user;
  private String token;

  public LoginVoluntaryResult(VoluntaryLoginResponseDTO user, String token) {
    this.user = user;
    this.token = token;
  }

  public VoluntaryLoginResponseDTO getUser() {
    return user;
  }

  public String getToken() {
    return token;
  }
}
