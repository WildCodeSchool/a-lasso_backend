package com.back_alasso.Activity;

import com.back_alasso.ActivityVoluntary.ActivityVoluntary;
import com.back_alasso.ActivityVoluntary.ActivityVoluntaryDTO;
import com.back_alasso.Association.AssociationDTO;
import com.back_alasso.Theme.ThemeNameEnumType;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record ActivityDTO(
  UUID id,
  String title,
  String description,
  List<String> image,
  AssociationDTO association,
  String location,
  Date date,
  ActivityVoluntaryDTO participants,
  List<ThemeNameEnumType> theme,
  boolean isSaved
) {
  private static final Logger log = LoggerFactory.getLogger(ActivityDTO.class);

  public static ActivityDTO fromEntityToDTO(Activity activity) {
    return new ActivityDTO(
      activity.getId(),
      activity.getTitle(),
      activity.getDescription(),
      activity.getActivityImages() != null
        ? activity.getActivityImages().stream().map(activityImage -> activityImage.getImage().getUrl()).toList()
        : null,
      activity.getAssociation() != null ? AssociationDTO.getAssociationDTO(activity.getAssociation()) : null,
      activity.getAddress().getCity(),
      activity.getDate(),
      ActivityVoluntaryDTO.convertToDTO(activity),
      activity.getActivityThemes().stream().map(activityTheme -> activityTheme.getTheme().getName()).toList(),
      activity
        .getActivityVoluntaries()
        .stream()
        .filter(activityVoluntary -> {
          String authenticatedUserId = "TODO -->"; // getUserAuthentificated();
          if (authenticatedUserId == null) {
            return false;
          }
          return activityVoluntary.getVoluntary().getId().equals(authenticatedUserId);
        })
        .findFirst()
        .map(ActivityVoluntary::isIs_saved)
        .orElse(false)
    );
  }
}
