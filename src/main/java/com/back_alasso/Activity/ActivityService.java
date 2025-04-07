package com.back_alasso.Activity;

import com.back_alasso.ActivityVoluntary.ActivityVoluntary;
import com.back_alasso.ActivityVoluntary.ActivityVoluntaryRepository;
import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.Voluntary.VoluntaryRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

  private final ActivityRepository activityRepository;
  private final VoluntaryRepository voluntaryRepository;
  private final ActivityVoluntaryRepository activityVoluntaryRepository;

  public ActivityService(
    ActivityRepository activityRepository,
    VoluntaryRepository voluntaryRepository,
    ActivityVoluntaryRepository activityVoluntaryRepository
  ) {
    this.activityRepository = activityRepository;
    this.voluntaryRepository = voluntaryRepository;
    this.activityVoluntaryRepository = activityVoluntaryRepository;
  }

  // service private shared methods

  private ActivityVoluntary getActivityVoluntary(UUID authenticatedUser, UUID activityId) {
    return activityVoluntaryRepository.findByVoluntary_idAndActivity_id(authenticatedUser, activityId).orElse(null);
  }

  private Voluntary getVoluntary(UUID authenticatedUser) {
    return voluntaryRepository.findById(authenticatedUser).orElseThrow(() -> new ResourceNotFoundException("Voluntary not found"));
  }

  private Activity getActivity(UUID activityId) {
    return activityRepository.findById(activityId).orElseThrow(() -> new ResourceNotFoundException("Activity not found"));
  }

  // service public methods

  public List<ActivityDTO> getAllActivities() {
    List<Activity> activities = activityRepository.findAll();
    return activities.stream().map(ActivityDTO::fromEntityToDTO).collect(Collectors.toList());
  }

  public Boolean updatedFavoriteStatus(UUID activityId, boolean isFavorite, UUID authenticatedUser) {
    ActivityVoluntary activityVoluntary = getActivityVoluntary(authenticatedUser, activityId);

    if (activityVoluntary != null) {
      activityVoluntary.setIs_saved(isFavorite);
      activityVoluntaryRepository.save(activityVoluntary);
      return isFavorite;
    } else {
      Voluntary voluntary = getVoluntary(authenticatedUser);
      Activity activity = getActivity(activityId);
      ActivityVoluntary newActivityVoluntary = new ActivityVoluntary(isFavorite, false, voluntary, activity);
      activityVoluntaryRepository.save(newActivityVoluntary);
      return isFavorite;
    }
  }

  public Boolean updatedRegisterStatus(UUID activityId, boolean isRegistered, UUID authenticatedUser) {
    ActivityVoluntary activityVoluntary = getActivityVoluntary(authenticatedUser, activityId);
    if (activityVoluntary != null) {
      activityVoluntary.setIs_registered(isRegistered);
      activityVoluntaryRepository.save(activityVoluntary);
      return isRegistered;
    } else {
      Voluntary voluntary = getVoluntary(authenticatedUser);
      Activity activity = getActivity(activityId);
      ActivityVoluntary newActivityVoluntary = new ActivityVoluntary(false, isRegistered, voluntary, activity);
      activityVoluntaryRepository.save(newActivityVoluntary);
      return isRegistered;
    }
  }
}
