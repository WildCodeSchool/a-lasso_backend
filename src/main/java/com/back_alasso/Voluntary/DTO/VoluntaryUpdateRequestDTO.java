package com.back_alasso.Voluntary.DTO;

import java.time.LocalDate;

public record VoluntaryUpdateRequestDTO(
  String first_name,
  String last_name,
  LocalDate birth_date,
  String city,
  String country,
  String mobile_phone
) {}
