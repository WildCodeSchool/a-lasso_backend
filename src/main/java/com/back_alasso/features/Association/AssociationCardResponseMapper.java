package com.back_alasso.features.Association;

import com.back_alasso.features.Association.DTO.AssociationCardResponseDTO;
import com.back_alasso.features.AssociationFollower.AssociationFollower;
import com.back_alasso.features.Image.DTO.ImageResponseDTO;
import com.back_alasso.features.Image.ImageEnumType;
import com.back_alasso.features.Image.ImageMapper;
import com.back_alasso.features.Statistic.StatisticDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AssociationCardResponseMapper {

  private final ImageMapper imageMapper;

  public AssociationCardResponseMapper(ImageMapper imageMapper) {
    this.imageMapper = imageMapper;
  }

  public AssociationCardResponseDTO fromEntityToDTO(Association association, UUID authenticatedUserId) {
    List<UUID> profileImageIds = association
      .getAssociationImages()
      .stream()
      .filter(ai -> ai.getImage().getType() == ImageEnumType.PROFILE_ASSOCIATION)
      .map(ai -> ai.getImage().getId())
      .toList();

    List<UUID> logoImageIds = association
      .getAssociationImages()
      .stream()
      .filter(ai -> ai.getImage().getType() == ImageEnumType.LOGO)
      .map(ai -> ai.getImage().getId())
      .toList();

    ImageResponseDTO profileImage = profileImageIds.isEmpty()
      ? new ImageResponseDTO(null, "defaultAssociationProfileImage.png")
      : imageMapper.toResponseDTOs(profileImageIds, ImageEnumType.PROFILE_ASSOCIATION).get(0);

    ImageResponseDTO logoImage = logoImageIds.isEmpty()
      ? new ImageResponseDTO(null, "defaultAssociationLogoImage.png")
      : imageMapper.toResponseDTOs(logoImageIds, ImageEnumType.LOGO).get(0);

    List<StatisticDTO> statistics = association.getStatistic().stream().map(StatisticDTO::fromEntityToDTO).toList();

    Boolean isFollow = association
      .getAssociationFollowers()
      .stream()
      .filter(follower -> follower.getVoluntary().getId().equals(authenticatedUserId))
      .findFirst()
      .map(AssociationFollower::isFollow)
      .orElse(false);

    return new AssociationCardResponseDTO(
      association.getId(),
      association.getDescription(),
      association.getFounder(),
      association.getFoundationDate(),
      association.getName(),
      profileImage,
      logoImage,
      association.getSiteURL(),
      statistics,
      isFollow
    );
  }
}
