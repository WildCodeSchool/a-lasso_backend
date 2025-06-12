package com.back_alasso.Association.DTO;

import com.back_alasso.Address.Address;
import com.back_alasso.Geolocation.GeolocationLoginDTO;
import com.back_alasso.Image.DTO.ImageResponseDTO;
import com.back_alasso.Statistic.StatisticDTO;
import com.back_alasso.UserMessage.UserMessageNotificationDTO;
import com.back_alasso.shared.NotificationDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AssociationLoginResponseDTO(
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
