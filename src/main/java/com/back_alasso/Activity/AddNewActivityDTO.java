package com.back_alasso.Activity;

import com.back_alasso.Theme.ThemeNameEnumType;
import java.time.LocalDateTime;
import java.util.List;

public record AddNewActivityDTO(
  List<ImageNewActivityDTO> images,
  String title,
  Long requestedVolunteers,
  LocalDateTime dateTime,
  Integer houseNumber,
  String streetName,
  String zipCode,
  String city,
  String country,
  List<ThemeNameEnumType> themes,
  String description
) {}
