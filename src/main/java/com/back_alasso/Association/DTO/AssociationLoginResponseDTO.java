package com.back_alasso.Association.DTO;

import com.back_alasso.Address.Address;
import com.back_alasso.Geolocation.GeolocationLoginDTO;
import com.back_alasso.Image.DTO.ImageResponseDTO;
import com.back_alasso.UserMessage.UserMessageNotificationDTO;
import java.time.LocalDate;
import java.util.List;

public record AssociationLoginResponseDTO(
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
  List<UserMessageNotificationDTO> messageNotifications
) {}
