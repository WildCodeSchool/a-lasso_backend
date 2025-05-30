package com.back_alasso.Association;

import com.back_alasso.Association.DTO.AssociationLoginResponseDTO;
import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.Geolocation.GeolocationLoginDTO;
import com.back_alasso.Image.DTO.ImageResponseDTO;
import com.back_alasso.Image.ImageEnumType;
import com.back_alasso.Image.ImageMapper;
import com.back_alasso.UserMessage.UserMessageNotificationDTO;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AssociationLoginResponseMapper {

  private final ImageMapper imageMapper;

  public AssociationLoginResponseMapper(ImageMapper imageMapper) {
    this.imageMapper = imageMapper;
  }

  public AssociationLoginResponseDTO fromEntityToDTO(Association association) {
    UUID profileImageId = association
      .getAssociationImages()
      .stream()
      .filter(img -> img.getImage().getType() == ImageEnumType.PROFILE_ASSOCIATION)
      .map(img -> img.getImage().getId())
      .findFirst()
      .orElseThrow(() -> new ResourceNotFoundException("Association profile image not found"));

    UUID logoImageId = association
      .getAssociationImages()
      .stream()
      .filter(img -> img.getImage().getType() == ImageEnumType.LOGO)
      .map(img -> img.getImage().getId())
      .findFirst()
      .orElseThrow(() -> new ResourceNotFoundException("Association logo image not found"));

    ImageResponseDTO profileImage = imageMapper
      .toResponseDTOs(List.of(profileImageId), ImageEnumType.PROFILE_ASSOCIATION)
      .stream()
      .findFirst()
      .orElse(null);

    ImageResponseDTO logoImage = imageMapper.toResponseDTOs(List.of(logoImageId), ImageEnumType.LOGO).stream().findFirst().orElse(null);

    List<UserMessageNotificationDTO> messageNotifications = association
      .getUserMessages()
      .stream()
      .filter(userMessage -> !userMessage.isRead())
      .collect(Collectors.groupingBy(userMessage -> userMessage.getMessage().getActivity(), Collectors.counting()))
      .entrySet()
      .stream()
      .map(entry -> new UserMessageNotificationDTO(entry.getKey().getId(), entry.getKey().getTitle(), entry.getValue().intValue()))
      .toList();

    return new AssociationLoginResponseDTO(
      "association",
      association.getEmail(),
      association.getName(),
      association.getDescription(),
      association.getFounder(),
      association.getFoundationDate(),
      association.getSiteURL(),
      association.getAddress(),
      profileImage,
      logoImage,
      GeolocationLoginDTO.from(association.getGeolocation()),
      messageNotifications
    );
  }
}
