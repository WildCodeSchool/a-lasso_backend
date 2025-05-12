package com.back_alasso.Association;

import com.back_alasso.Address.Address;
import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.Geolocation.GeolocationLoginDTO;
import com.back_alasso.Image.ImageEnumType;
import com.back_alasso.UserMessage.UserMessageNotificationDTO;
import com.back_alasso.shared.NotificationDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record AssociationLoginDTO(
  String type,
  String email,
  String name,
  String description,
  String founder,
  LocalDate foundationDate,
  String siteURL,
  Address address,
  String associationProfileImageURL,
  String associationLogoImage,
  GeolocationLoginDTO geolocation,
  NotificationDTO notification
) {
  public static AssociationLoginDTO fromEntityToDTO(Association association) {
    List<UserMessageNotificationDTO> messageNotifications = association
      .getUserMessages()
      .stream()
      .filter(userMessage -> !userMessage.isRead())
      .collect(Collectors.groupingBy(userMessage -> userMessage.getMessage().getActivity(), Collectors.counting()))
      .entrySet()
      .stream()
      .map(entry -> new UserMessageNotificationDTO(entry.getKey().getId(), entry.getKey().getTitle(), entry.getValue().intValue()))
      .toList();

    NotificationDTO notifications = new NotificationDTO(messageNotifications, null);

    return new AssociationLoginDTO(
      "association",
      association.getEmail(),
      association.getName(),
      association.getDescription(),
      association.getFounder(),
      association.getFoundationDate(),
      association.getSiteURL(),
      association.getAddress(),
      association
        .getAssociationImages()
        .stream()
        .filter(associationImage -> associationImage.getImage().getType() == ImageEnumType.PROFILE_ASSOCIATION)
        .map(associationImage -> associationImage.getImage().getUrl())
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Association profile image not found")),
      association
        .getAssociationImages()
        .stream()
        .filter(associationImage -> associationImage.getImage().getType() == ImageEnumType.LOGO)
        .map(associationImage -> associationImage.getImage().getUrl())
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Association logo image not found")),
      GeolocationLoginDTO.from(association.getGeolocation()),
      notifications
    );
  }
}
