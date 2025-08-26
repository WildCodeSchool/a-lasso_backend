package com.back_alasso.features.Association.DTO;

import com.back_alasso.features.Address.Address;
import com.back_alasso.features.Geolocation.DTO.GeolocationLoginDTO;
import com.back_alasso.features.Image.DTO.ImageResponseDTO;
import com.back_alasso.features.Statistic.StatisticDTO;
import com.back_alasso.features.UserMessage.UserMessageNotificationDTO;
import com.back_alasso.shared.NotificationDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AssociationGeneralInfoRequestDTO(
  @NotBlank(message = "le nom du fondateur est obligatoire") String founder,
  @NotNull(message = "la date de création est obligatoire") LocalDate foundationDate
) {
  public static record AssociationLoginResponseDTO(
    UUID id,
    String type,
    String email,
    String name,
    String description,
    String founder,
    LocalDate foundationDate,
    String siteURL,
    Address address,
    ImageResponseDTO associationProfileImage,
    ImageResponseDTO associationLogoImage,
    GeolocationLoginDTO geolocation,
    List<UserMessageNotificationDTO> messageNotifications,
    LocalDate createdAt,
    NotificationDTO notification,
    List<StatisticDTO> statistics
  ) {}
}
