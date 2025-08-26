package com.back_alasso.features.ActivityVoluntary;

import com.back_alasso.exception.ResourceNotFoundException;
import com.back_alasso.features.Activity.Activity;
import com.back_alasso.features.Activity.ActivityRepository;
import com.back_alasso.features.Activity.ActivityService;
import com.back_alasso.features.ActivityVoluntary.DTO.ActivityParticipantsRequestDTO;
import com.back_alasso.features.Voluntary.Voluntary;
import com.back_alasso.features.Voluntary.VoluntaryService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ActivityVoluntaryService {

  private final ActivityRepository activityRepository;
  private final ActivityVoluntaryRepository activityVoluntaryRepository;
  private final ActivityService activityService;
  private final VoluntaryService voluntaryService;

  public ActivityVoluntaryService(
    ActivityRepository activityRepository,
    ActivityVoluntaryRepository activityVoluntaryRepository,
    ActivityService activityService,
    VoluntaryService voluntaryService
  ) {
    this.activityRepository = activityRepository;
    this.activityVoluntaryRepository = activityVoluntaryRepository;
    this.activityService = activityService;
    this.voluntaryService = voluntaryService;
  }

  public ActivityParticipantsRequestDTO getActivityVoluntary(UUID activityId) {
    Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResourceNotFoundException("Activity not found"));

    return ActivityParticipantsRequestDTO.convertToDTO(activity);
  }

  public ActivityVoluntary getActivityVoluntaryByIds(UUID authenticatedUser, UUID activityId) {
    return activityVoluntaryRepository.findByVoluntary_idAndActivity_id(authenticatedUser, activityId).orElse(null);
  }

  public Boolean updatedFavoriteStatus(UUID activityId, boolean isFavorite, UUID authenticatedUserId) {
    ActivityVoluntary activityVoluntary = getActivityVoluntaryByIds(authenticatedUserId, activityId);
    if (activityVoluntary != null) {
      activityVoluntary.setSaved(isFavorite);
      activityVoluntaryRepository.save(activityVoluntary);
      return isFavorite;
    } else {
      Voluntary voluntary = voluntaryService.getVoluntaryById(authenticatedUserId);
      Activity activity = activityService.getActivityById(activityId);
      ActivityVoluntary newActivityVoluntary = new ActivityVoluntary(isFavorite, false, voluntary, activity);
      activityVoluntaryRepository.save(newActivityVoluntary);
      return isFavorite;
    }
  }

  public Boolean updatedRegisterStatus(UUID activityId, boolean isRegistered, UUID authenticatedUserId) {
    ActivityVoluntary activityVoluntary = getActivityVoluntaryByIds(authenticatedUserId, activityId);
    if (activityVoluntary != null) {
      activityVoluntary.setRegistered(isRegistered);
      activityVoluntaryRepository.save(activityVoluntary);
      return isRegistered;
    } else {
      Voluntary voluntary = voluntaryService.getVoluntaryById(authenticatedUserId);
      Activity activity = activityService.getActivityById(activityId);
      ActivityVoluntary newActivityVoluntary = new ActivityVoluntary(false, isRegistered, voluntary, activity);
      activityVoluntaryRepository.save(newActivityVoluntary);
      return isRegistered;
    }
  }
}
