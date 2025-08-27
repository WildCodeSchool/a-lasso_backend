package com.back_alasso.features.Activity;

import com.back_alasso.features.Activity.DTO.ActivityResponseDTO;
import com.back_alasso.features.ActivityVoluntary.ActivityVoluntary;
import com.back_alasso.features.ActivityVoluntary.DTO.ActivityParticipantsRequestDTO;
import com.back_alasso.features.Address.DTO.AddressResponseDTO;
import com.back_alasso.features.Association.DTO.AssociationActivityRequestDTO;
import com.back_alasso.features.Geolocation.DTO.GeolocationRequestDTO;
import com.back_alasso.features.Image.ImageEnumType;
import com.back_alasso.features.Image.ImageMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivityResponseMapper {

  private final ImageMapper imageMapper;

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
      activity.getAssociation() != null ? AssociationActivityRequestDTO.getAssociationDTO(activity.getAssociation()) : null,
      GeolocationRequestDTO.getCoordinates(activity),
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
