package com.back_alasso.features.Activity;

import com.back_alasso.exception.ResourceNotFoundException;
import com.back_alasso.features.Activity.DTO.ActivityResponseDTO;
import com.back_alasso.features.Activity.DTO.ActivitySaveRequestDTO;
import com.back_alasso.features.Activity.DTO.ActivityStatusEnumType;
import com.back_alasso.features.ActivityImage.ActivityImageService;
import com.back_alasso.features.ActivityTheme.ActivityThemeService;
import com.back_alasso.features.Address.Address;
import com.back_alasso.features.Address.AddressService;
import com.back_alasso.features.Association.Association;
import com.back_alasso.features.Association.AssociationService;
import com.back_alasso.features.Geolocation.Geolocation;
import com.back_alasso.features.Geolocation.GeolocationService;
import com.back_alasso.features.Image.Image;
import com.back_alasso.features.Image.ImageService;
import com.back_alasso.features.Theme.Theme;
import com.back_alasso.features.Theme.ThemeService;
import com.back_alasso.features.User.UserEnumType;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {

  private final ActivityRepository activityRepository;
  private final ActivityResponseMapper activityResponseMapper;
  private final AddressService addressService;
  private final ImageService imageService;
  private final ActivityImageService activityImageService;
  private final ThemeService themeService;
  private final ActivityThemeService activityThemeService;
  private final GeolocationService geolocationService;
  private final AssociationService associationService;

  public Activity getActivityById(UUID activityId) {
    return activityRepository.findByIdFromNotBannedAssociation(activityId).orElseThrow(() -> new ResourceNotFoundException("Activity not found"));
  }

  public List<ActivityResponseDTO> getFutureActivities(UUID authenticatedUserId) {
    List<Activity> activities = activityRepository.findAllFromNotBannedAssociationsAndFuture(LocalDateTime.now());

    if (activities.isEmpty()) {
      throw new ResourceNotFoundException("No future activities found");
    }

    return activities.stream().map(activity -> activityResponseMapper.fromEntityToDTO(activity, authenticatedUserId)).toList();
  }

  public List<ActivityResponseDTO> getPastActivities(UUID authenticatedUserId) {
    List<Activity> activities = activityRepository.findAllFromNotBannedAssociationsAndPast(LocalDateTime.now());

    if (activities.isEmpty()) {
      throw new ResourceNotFoundException("No past activities found");
    }

    return activities.stream().map(activity -> activityResponseMapper.fromEntityToDTO(activity, authenticatedUserId)).toList();
  }

  public List<ActivityResponseDTO> getDraftActivities(UUID authenticatedUserId) {
    List<Activity> activities = activityRepository.findAllFromNotBannedAssociationsAndDraft(ActivityStatusEnumType.draft);

    return activities.stream().map(activity -> activityResponseMapper.fromEntityToDTO(activity, authenticatedUserId)).toList();
  }

  public ActivityResponseDTO getMappedActivityById(UUID authenticatedUserId, UUID activityId) {
    Activity activity = getActivityById(activityId);

    return activityResponseMapper.fromEntityToDTO(activity, authenticatedUserId);
  }

  @Transactional
  public ActivityResponseDTO saveOrUpdateActivity(ActivitySaveRequestDTO dto, UUID associationId) {
    Association association = associationService.getAssociationById(associationId);

    Address address = addressService.createOrRetrieveAddress(dto);
    Geolocation geolocation = geolocationService.createGeolocation(dto);

    Boolean isUpdate = dto.getId() != null;

    Activity activity;
    if (isUpdate) {
      activity = activityRepository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException("Activity not found for update"));

      activity.setStatus(dto.getStatus());
      activity.setTitle(dto.getTitle());
      activity.setDate(dto.getDateTime());
      activity.setDescription(dto.getDescription());
      activity.setVoluntariesRequest(dto.getRequestedVolunteers());
      activity.setAddress(address);
      activity.setGeolocation(geolocation);
    } else {
      activity = new Activity(
        dto.getStatus(),
        dto.getTitle(),
        dto.getDateTime(),
        dto.getDescription(),
        dto.getRequestedVolunteers(),
        association,
        address,
        null,
        null
      );
      activity.setGeolocation(geolocation);
      activity = activityRepository.save(activity);
    }

    List<Theme> themes = themeService.getThemes(dto.getThemes());
    activityThemeService.linkThemesToActivity(activity, themes);

    List<Image> images = imageService.processImages(dto.getImages());
    activityImageService.linkImagesToActivity(activity, images);

    return activityResponseMapper.fromEntityToDTO(activity, associationId);
  }

  @Transactional
  public void deleteActivity(UUID activityId, UUID authenticatedUserId) {
    Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResourceNotFoundException("Activity not found"));

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    boolean isAdmin = authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(UserEnumType.ROLE_ADMIN.name()));

    if (!isAdmin && !activity.getAssociation().getId().equals(authenticatedUserId)) {
      throw new SecurityException("You are not allowed to delete this activity");
    }

    activityRepository.delete(activity);
  }

  public List<ActivityResponseDTO> getActivitiesByAssociationId(UUID associationId, UUID authenticatedUserId) {
    List<Activity> activities = activityRepository.findByAssociationId(associationId);
    return activities.stream().map(activity -> activityResponseMapper.fromEntityToDTO(activity, authenticatedUserId)).toList();
  }
}
