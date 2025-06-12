package com.back_alasso.Voluntary;

import com.back_alasso.ActivityVoluntary.ActivityVoluntaryLoginDTO;
import com.back_alasso.AssociationFollower.AssociationFollower;
import com.back_alasso.AssociationFollower.AssociationFollowerDTO;
import com.back_alasso.Geolocation.GeolocationLoginDTO;
import com.back_alasso.Image.DTO.ImageResponseDTO;
import com.back_alasso.Image.ImageEnumType;
import com.back_alasso.Image.ImageMapper;
import com.back_alasso.UserMessage.UserMessageNotificationDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class VoluntaryLoginResponseMapper {

  private final ImageMapper imageMapper;

  public VoluntaryLoginResponseMapper(ImageMapper imageMapper) {
    this.imageMapper = imageMapper;
  }

  public VoluntaryLoginResponseDTO fromEntityToDTO(Voluntary voluntary) {
    ImageResponseDTO avatar = voluntary.getAvatar() != null
      ? imageMapper.toResponseDTOs(List.of(voluntary.getAvatar().getId()), ImageEnumType.AVATAR).stream().findFirst().orElse(null)
      : null;

    List<AssociationFollowerDTO> followed = voluntary
      .getAssociationFollowers()
      .stream()
      .filter(AssociationFollower::isFollow)
      .map(AssociationFollowerDTO::fromEntityToDTO)
      .toList();

    List<ActivityVoluntaryLoginDTO> activitiesUserInfos = voluntary
      .getActivityVoluntaries()
      .stream()
      .map(ActivityVoluntaryLoginDTO::fromEntityToDTO)
      .toList();

    List<UserMessageNotificationDTO> messageNotifications = voluntary
      .getUserMessages()
      .stream()
      .filter(userMessage -> !userMessage.isRead())
      .collect(Collectors.groupingBy(userMessage -> userMessage.getMessage().getActivity(), Collectors.counting()))
      .entrySet()
      .stream()
      .map(entry -> new UserMessageNotificationDTO(entry.getKey().getId(), entry.getKey().getTitle(), entry.getValue().intValue()))
      .toList();

    return new VoluntaryLoginResponseDTO(
      "voluntary",
      voluntary.getEmail(),
      voluntary.getFirst_name(),
      voluntary.getLast_name(),
      voluntary.getMobile_phone(),
      voluntary.getCity(),
      voluntary.getCountry(),
      avatar,
      followed,
      activitiesUserInfos,
      messageNotifications,
      GeolocationLoginDTO.from(voluntary.getGeolocation()),
      voluntary.getCreatedAt().toLocalDate()
    );
  }
}
