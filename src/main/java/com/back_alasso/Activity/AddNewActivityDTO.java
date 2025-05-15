package com.back_alasso.Activity;

import com.back_alasso.Address.AddressRequestDTO;
import com.back_alasso.Theme.ThemeNameEnumType;
import java.time.LocalDateTime;
import java.util.List;

public record AddNewActivityDTO(
  List<ImageNewActivityDTO> images,
  String title,
  Long requestedVolunteers,
  LocalDateTime dateTime,
  AddressRequestDTO address,
  List<ThemeNameEnumType> themes,
  String description
) {}
