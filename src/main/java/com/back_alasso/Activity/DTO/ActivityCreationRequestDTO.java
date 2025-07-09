package com.back_alasso.Activity.DTO;

import com.back_alasso.Address.AddressRequestDTO;
import com.back_alasso.Image.DTO.ImageActivityCreationRequestDTO;
import com.back_alasso.Theme.ThemeNameEnumType;
import java.time.LocalDateTime;
import java.util.List;

public record ActivityCreationRequestDTO(
  List<ImageActivityCreationRequestDTO> images,
  String title,
  Long requestedVolunteers,
  LocalDateTime dateTime,
  AddressRequestDTO address,
  List<ThemeNameEnumType> themes,
  String description
) {}
