package com.back_alasso.Activity;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities")
public class ActivityController {

  private final ActivityService activityService;

  public ActivityController(ActivityService activityService) {
    this.activityService = activityService;
  }

  @GetMapping
  public ResponseEntity<List<ActivityDTO>> getAllActivities() {
    List<ActivityDTO> activities = activityService.getAllActivities();
    return ResponseEntity.status(HttpStatus.OK).body(activities);
  }
}
