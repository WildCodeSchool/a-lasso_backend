package com.back_alasso.features.Activity;

import com.back_alasso.exception.ResourceNotFoundException;
import com.back_alasso.features.Activity.DTO.ActivityResponseDTO;
import com.back_alasso.features.Activity.DTO.ActivitySaveRequestDTO;
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
import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
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

  public ActivityService(
    ActivityRepository activityRepository,
    ActivityResponseMapper activityResponseMapper,
    AddressService addressService,
    ImageService imageService,
    ActivityImageService activityImageService,
    ThemeService themeService,
    ActivityThemeService activityThemeService,
    GeolocationService geolocationService,
    AssociationService associationService
  ) {
    this.activityRepository = activityRepository;
    this.activityResponseMapper = activityResponseMapper;
    this.addressService = addressService;
    this.imageService = imageService;
    this.activityImageService = activityImageService;
    this.themeService = themeService;
    this.activityThemeService = activityThemeService;
    this.geolocationService = geolocationService;
    this.associationService = associationService;
  }

  public Activity getActivityById(UUID activityId) {
    return activityRepository.findByIdFromNotBannedAssociation(activityId).orElseThrow(() -> new ResourceNotFoundException("Activity not found"));
  }

  public List<ActivityResponseDTO> getAllActivities(UUID authenticatedUserId) {
    List<Activity> activities = activityRepository.findAllFromNotBannedAssociations();

    if (activities.isEmpty()) {
      throw new ResourceNotFoundException("activities not found");
    }

    return activities.stream().map(activity -> activityResponseMapper.fromEntityToDTO(activity, authenticatedUserId)).collect(Collectors.toList());
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

    if (!activity.getAssociation().getId().equals(authenticatedUserId)) {
      throw new SecurityException("You are not allowed to delete this activity");
    }

    activityRepository.delete(activity);
  }

  public List<ActivityResponseDTO> getActivitiesByAssociationId(UUID associationId, UUID authenticatedUserId) {
    List<Activity> activities = activityRepository.findByAssociationId(associationId);
    return activities.stream().map(activity -> activityResponseMapper.fromEntityToDTO(activity, authenticatedUserId)).toList();
  }
}
