package com.back_alasso.Activity;

import com.back_alasso.Theme.ThemeNameEnumType;
import java.util.List;
import java.util.UUID;

public record AddNewActivityDTO(
  UUID associationId,
  List<ImageNewActivityDTO> images,
  String title,
  String zipCode,
  List<ThemeNameEnumType> themes,
  String description
) {}
