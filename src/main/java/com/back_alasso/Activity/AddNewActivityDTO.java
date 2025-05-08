package com.back_alasso.Activity;

import com.back_alasso.Theme.ThemeNameEnumType;
import java.util.List;
import java.util.UUID;

public record AddNewActivityDTO(
  UUID associationId,
  List<ImageNewActivityDTO> images,
  String title,
  Long volontaries_request,
  String date,
  String hour,
  String zipCode,
  String City,
  List<ThemeNameEnumType> themes,
  String description
) {}
