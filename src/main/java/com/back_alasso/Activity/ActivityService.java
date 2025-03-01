package com.back_alasso.Activity;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

  private final ActivityRepository activityRepository;

  public ActivityService(ActivityRepository activityRepository) {
    this.activityRepository = activityRepository;
  }

  public List<ActivityDTO> getAllActivities() {
    List<Activity> activities = activityRepository.findAll();
    return activities.stream().map(ActivityDTO::fromEntityToDTO).collect(Collectors.toList());
  }
}
