package com.back_alasso.Activity.DTO;

import com.back_alasso.ActivityVoluntary.DTO.ActivityParticipantsRequestDTO;
import com.back_alasso.Address.DTO.AddressResponseDTO;
import com.back_alasso.Association.DTO.AssociationActivityRequestDTO;
import com.back_alasso.Geolocation.DTO.GeolocationRequestDTO;
import com.back_alasso.Image.DTO.ImageResponseDTO;
import com.back_alasso.Theme.ThemeNameEnumType;
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
