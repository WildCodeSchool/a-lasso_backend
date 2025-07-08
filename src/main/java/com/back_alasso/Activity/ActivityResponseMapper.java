package com.back_alasso.Activity;

import com.back_alasso.Activity.DTO.ActivityResponseDTO;
import com.back_alasso.ActivityVoluntary.ActivityParticipantsRequestDTO;
import com.back_alasso.ActivityVoluntary.ActivityVoluntary;
import com.back_alasso.Address.AddressResponseDTO;
import com.back_alasso.Association.DTO.AssociationActivityDTO;
import com.back_alasso.Geolocation.GeolocationDTO;
import com.back_alasso.Image.ImageEnumType;
import com.back_alasso.Image.ImageMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivityResponseMapper {

    private final ImageMapper imageMapper;

    @Autowired
    public ActivityResponseMapper(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    public ActivityResponseDTO fromEntityToDTO(Activity activity, UUID authenticatedUserId) {
        Optional<ActivityVoluntary> voluntary = getUserActivityVoluntaryStatus(activity, authenticatedUserId);

        List<UUID> imageIds = activity.getActivityImages().stream().map(activityImage -> activityImage.getImage().getId()).toList();


    return new ActivityResponseDTO(
      activity.getId(),
            activity.getStatus(),
      activity.getTitle(),
      activity.getDescription(),
            AddressResponseDTO.fromEntityToDTO(activity.getAddress()),
      imageMapper.toResponseDTOs(imageIds, ImageEnumType.ACTIVITY),
      activity.getAssociation() != null ? AssociationActivityDTO.getAssociationDTO(activity.getAssociation()) : null,
      GeolocationDTO.getCoordinates(activity),
      activity.getDate(),
            ActivityParticipantsRequestDTO.convertToDTO(activity),
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
