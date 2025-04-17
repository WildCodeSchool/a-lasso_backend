package com.back_alasso.Activity;

import com.back_alasso.ActivityVoluntary.ActivityVoluntary;
import com.back_alasso.ActivityVoluntary.ActivityVoluntaryDTO;
import com.back_alasso.Association.AssociationActivityDTO;
import com.back_alasso.Geolocation.GeolocationDTO;
import com.back_alasso.Theme.ThemeNameEnumType;
import java.time.LocalDateTime;
import java.util.*;

public record ActivityDTO(
        UUID id,
        String title,
        String description,
        List<ActivityImagesResponseDTO> images,
        AssociationActivityDTO association,
        GeolocationDTO location,
        LocalDateTime date,
        ActivityVoluntaryDTO participants,
        List<ThemeNameEnumType> themesName
) {
  public static ActivityDTO fromEntityToDTO(Activity activity, UUID authenticatedUserId) {
    Optional<ActivityVoluntary> voluntary = getUserActivityVoluntaryStatus(activity, authenticatedUserId);

        return new ActivityDTO(
                activity.getId(),
                activity.getTitle(),
                activity.getDescription(),
                ActivityImageMapper.toResponseDTOs(activity.getActivityImages()),
                activity.getAssociation() != null ? AssociationActivityDTO.getAssociationDTO(activity.getAssociation()) : null,
                GeolocationDTO.getCoordinates(activity),
                activity.getDate(),
                ActivityVoluntaryDTO.convertToDTO(activity),
                activity.getActivityThemes().stream().map(theme -> theme.getTheme().getName()).toList()
        );
    }

  private static Optional<ActivityVoluntary> getUserActivityVoluntaryStatus(Activity activity, UUID authenticatedUserId) {
    if (authenticatedUserId == null || activity.getActivityVoluntaries() == null) {
      return Optional.empty();
    }

    return activity.getActivityVoluntaries().stream().filter(av -> av.getVoluntary().getId().equals(authenticatedUserId)).findFirst();
  }
}
