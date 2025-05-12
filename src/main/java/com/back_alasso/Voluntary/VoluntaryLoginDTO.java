package com.back_alasso.Voluntary;

import com.back_alasso.ActivityVoluntary.ActivityVoluntaryLoginDTO;
import com.back_alasso.AssociationFollower.AssociationFollower;
import com.back_alasso.AssociationFollower.AssociationFollowerDTO;
import com.back_alasso.Country.Country;
import com.back_alasso.Geolocation.GeolocationLoginDTO;
import com.back_alasso.Image.Image;
import com.back_alasso.UserMessage.UserMessageNotificationDTO;
import com.back_alasso.shared.NotificationDTO;
import java.util.List;
import java.util.stream.Collectors;

public record VoluntaryLoginDTO(
  String type,
  String email,
  String first_name,
  String last_name,
  String mobile_phone,
  String city,
  Country country,
  Image avatar,
  List<AssociationFollowerDTO> followedAssociations,
  List<ActivityVoluntaryLoginDTO> activitiesUserInfos,
  NotificationDTO notification,
  GeolocationLoginDTO geolocation
) {
  public static VoluntaryLoginDTO fromEntityToDTO(Voluntary voluntary, Integer reportsInProgress) {
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

    NotificationDTO notifications = new NotificationDTO(messageNotifications, reportsInProgress);

    return new VoluntaryLoginDTO(
      "voluntary",
      voluntary.getEmail(),
      voluntary.getFirst_name(),
      voluntary.getLast_name(),
      voluntary.getMobile_phone(),
      voluntary.getCity(),
      voluntary.getCountry(),
      voluntary.getAvatar(),
      followed,
      activitiesUserInfos,
      notifications,
      GeolocationLoginDTO.from(voluntary.getGeolocation())
    );
  }
}
