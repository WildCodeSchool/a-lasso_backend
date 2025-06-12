package com.back_alasso.Voluntary;

import com.back_alasso.ActivityVoluntary.ActivityVoluntaryLoginDTO;
import com.back_alasso.AssociationFollower.AssociationFollowerDTO;
import com.back_alasso.Country.Country;
import com.back_alasso.Geolocation.GeolocationLoginDTO;
import com.back_alasso.Image.DTO.ImageResponseDTO;
import com.back_alasso.shared.NotificationDTO;

import java.util.List;
import java.time.LocalDate;

public record VoluntaryLoginResponseDTO(
  String type,
  String email,
  String first_name,
  String last_name,
  String mobile_phone,
  String city,
  Country country,
  ImageResponseDTO avatar,
  List<AssociationFollowerDTO> followedAssociations,
  List<ActivityVoluntaryLoginDTO> activitiesUserInfos,
  NotificationDTO notification,
  GeolocationLoginDTO geolocation,
  LocalDate createdAt
) {}
