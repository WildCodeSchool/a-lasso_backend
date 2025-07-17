package com.back_alasso.features.Activity.DTO;

import com.back_alasso.features.ActivityVoluntary.DTO.ActivityParticipantsRequestDTO;
import com.back_alasso.features.Address.DTO.AddressResponseDTO;
import com.back_alasso.features.Association.DTO.AssociationActivityRequestDTO;
import com.back_alasso.features.Geolocation.DTO.GeolocationRequestDTO;
import com.back_alasso.features.Image.DTO.ImageResponseDTO;
import com.back_alasso.features.Theme.ThemeNameEnumType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ActivityResponseDTO(
  UUID id,
  ActivityStatusEnumType status,
  String title,
  String description,
  AddressResponseDTO address,
  List<ImageResponseDTO> images,
  AssociationActivityRequestDTO association,
  GeolocationRequestDTO location,
  LocalDateTime date,
  ActivityParticipantsRequestDTO participants,
  List<ThemeNameEnumType> themesName
) {}
