package com.back_alasso.Activity;

import com.back_alasso.User.UserService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activities")
public class ActivityController {

  private final ActivityService activityService;
  private final UserService userService;

  public ActivityController(ActivityService activityService, UserService userService) {
    this.activityService = activityService;
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<ActivityDTO>> getAllActivities() {
    List<ActivityDTO> activities = activityService.getAllActivities();
    return ResponseEntity.status(HttpStatus.OK).body(activities);
  }

  @PatchMapping("/{activityId}/updateFavorite")
  public ResponseEntity<Boolean> patchFavoriteStatus(
    @PathVariable UUID activityId,
    @RequestBody UpdateFavoriteRequestDTO request,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    String emailAuthentificatedUser = userDetails.getUsername();
    UUID authenticatedUser = userService.findByEmail(emailAuthentificatedUser).getId();
    boolean updatedFavoriteStatus = activityService.updatedFavoriteStatus(activityId, request.isFavorite(), authenticatedUser);
    return ResponseEntity.status(HttpStatus.OK).body(updatedFavoriteStatus);
  }
}
