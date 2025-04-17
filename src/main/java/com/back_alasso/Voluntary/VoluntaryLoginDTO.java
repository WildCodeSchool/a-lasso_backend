package com.back_alasso.Voluntary;

import com.back_alasso.ActivityVoluntary.ActivityVoluntaryLoginDTO;
import com.back_alasso.AssociationFollower.AssociationFollower;
import com.back_alasso.AssociationFollower.AssociationFollowerDTO;
import com.back_alasso.Country.Country;
import com.back_alasso.Geolocation.GeolocationLoginDTO;
import com.back_alasso.Image.Image;
import java.util.List;

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
  GeolocationLoginDTO geolocation
) {
  public static VoluntaryLoginDTO fromEntityToDTO(Voluntary voluntary) {
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
      GeolocationLoginDTO.from(voluntary.getGeolocation())
    );
  }
}
