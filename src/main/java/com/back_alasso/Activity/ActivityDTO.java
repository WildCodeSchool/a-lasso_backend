package com.back_alasso.Activity;

import com.back_alasso.ActivityVoluntary.ActivityVoluntary;
import com.back_alasso.ActivityVoluntary.ActivityVoluntaryDTO;
import com.back_alasso.Association.AssociationActivityDTO;
import com.back_alasso.Geolocalisation.GeolocalisationDTO;
import com.back_alasso.Theme.ThemeNameEnumType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record ActivityDTO(
  UUID id,
  String title,
  String description,
  List<String> images,
  AssociationActivityDTO association,
  GeolocalisationDTO location,
  LocalDateTime date,
  ActivityVoluntaryDTO participants,
  List<ThemeNameEnumType> theme,
  boolean isSaved,
  boolean isRegistered
) {
  public static ActivityDTO fromEntityToDTO(Activity activity) {
    Optional<ActivityVoluntary> voluntary = activity
      .getActivityVoluntaries()
      .stream()
      .filter(activityVoluntary -> {
        String authenticatedUserId = "TODO -->"; // getUserAuthenticated();
        if (authenticatedUserId == null) {
          return false;
        }
        return activityVoluntary.getVoluntary().getId().equals(authenticatedUserId);
      })
      .findFirst();

    return new ActivityDTO(
      activity.getId(),
      activity.getTitle(),
      activity.getDescription(),
      activity.getActivityImages() != null ? activity.getActivityImages().stream().map(image -> image.getImage().getUrl()).toList() : null,
      activity.getAssociation() != null ? AssociationActivityDTO.getAssociationDTO(activity.getAssociation()) : null,
      GeolocalisationDTO.getCoordinates(activity),
      activity.getDate(),
      ActivityVoluntaryDTO.convertToDTO(activity),
      activity.getActivityThemes().stream().map(theme -> theme.getTheme().getName()).toList(),
      voluntary.map(ActivityVoluntary::isIs_saved).orElse(false),
      voluntary.map(ActivityVoluntary::isIs_registered).orElse(false)
    );
  }
}
