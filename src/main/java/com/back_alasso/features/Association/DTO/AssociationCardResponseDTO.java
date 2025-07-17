package com.back_alasso.features.Association.DTO;

import com.back_alasso.features.Image.DTO.ImageResponseDTO;
import com.back_alasso.features.Statistic.StatisticDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AssociationCardResponseDTO(
  UUID id,
  String description,
  String founder,
  LocalDate foundationDate,
  String name,
  ImageResponseDTO associationProfileImage,
  ImageResponseDTO associationLogoImage,
  String siteURL,
  List<StatisticDTO> statistics
) {}
