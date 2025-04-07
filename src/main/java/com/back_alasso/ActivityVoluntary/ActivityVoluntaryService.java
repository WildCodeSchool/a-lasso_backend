package com.back_alasso.ActivityVoluntary;

import com.back_alasso.Activity.Activity;
import com.back_alasso.Activity.ActivityRepository;
import com.back_alasso.Exception.ResourceNotFoundException;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ActivityVoluntaryService {

  private final ActivityRepository activityRepository;

  public ActivityVoluntaryService(ActivityRepository activityRepository) {
    this.activityRepository = activityRepository;
  }

  public ActivityVoluntaryDTO getActivityVoluntary(UUID activityId) {
    Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResourceNotFoundException("Activity not found"));

    return ActivityVoluntaryDTO.convertToDTO(activity);
  }
}
