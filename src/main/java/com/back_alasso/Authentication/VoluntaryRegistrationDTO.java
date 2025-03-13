package com.back_alasso.Authentication;

import java.time.LocalDate;
import java.util.Optional;

public record VoluntaryRegistrationDTO(
  String first_name,
  String last_name,
  String email,
  String password,
  Optional<String> mobile_phone,
  String city,
  String country,
  LocalDate birth_date
) {}
