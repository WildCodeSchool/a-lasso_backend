package com.back_alasso.Activity.DTO;

import com.back_alasso.ActivityVoluntary.ActivityParticipantsRequestDTO;
import com.back_alasso.Address.AddressResponseDTO;
import com.back_alasso.Association.DTO.AssociationActivityDTO;
import com.back_alasso.Geolocation.GeolocationDTO;
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
        AssociationActivityDTO association,
        GeolocationDTO location,
        LocalDateTime date,
        ActivityParticipantsRequestDTO participants,
        List<ThemeNameEnumType> themesName
) {
}
