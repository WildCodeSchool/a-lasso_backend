package com.back_alasso.Activity;

import com.back_alasso.ActivityVoluntary.ActivityVoluntary;
import com.back_alasso.ActivityVoluntary.ActivityVoluntaryDTO;
import com.back_alasso.Association.AssociationActivityDTO;
import com.back_alasso.Geolocation.GeolocationDTO;
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
                activity.getActivityImages() != null ? activity.getActivityImages().stream().map(image -> image.getImage().getUrl()).toList() : null,
                activity.getAssociation() != null ? AssociationActivityDTO.getAssociationDTO(activity.getAssociation()) : null,
                GeolocationDTO.getCoordinates(activity),
                activity.getDate(),
                ActivityVoluntaryDTO.convertToDTO(activity),
                activity.getActivityThemes().stream().map(theme -> theme.getTheme().getName()).toList()
        );
    }

    private static Optional<ActivityVoluntary> getUserActivityVoluntaryStatus(Activity activity, UUID authenticatedUserId) {
        if (authenticatedUserId == null) {
            return Optional.empty();
        }

        return activity.getActivityVoluntaries().stream().filter(av -> av.getVoluntary().getId().equals(authenticatedUserId)).findFirst();
    }
}
