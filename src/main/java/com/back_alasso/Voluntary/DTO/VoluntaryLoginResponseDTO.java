package com.back_alasso.Voluntary.DTO;

import com.back_alasso.ActivityVoluntary.DTO.ActivityVoluntaryLoginResponseDTO;
import com.back_alasso.AssociationFollower.AssociationFollowerDTO;
import com.back_alasso.Country.Country;
import com.back_alasso.Geolocation.DTO.GeolocationLoginDTO;
import com.back_alasso.Image.DTO.ImageResponseDTO;
import com.back_alasso.shared.NotificationDTO;
import java.time.LocalDate;
import java.util.List;

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
  List<ActivityVoluntaryLoginResponseDTO> activitiesUserInfos,
  NotificationDTO notification,
  GeolocationLoginDTO geolocation,
  LocalDate createdAt,
  LocalDate birth_date
) {}
