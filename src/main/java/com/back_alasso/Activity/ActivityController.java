package com.back_alasso.Activity;

import com.back_alasso.ActivityVoluntary.ActivityVoluntaryDTO;
import com.back_alasso.ActivityVoluntary.ActivityVoluntaryService;
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

  private final ActivityVoluntaryService activityVoluntaryService;

  public ActivityController(ActivityService activityService, UserService userService, ActivityVoluntaryService activityVoluntaryService) {
    this.activityService = activityService;
    this.userService = userService;
    this.activityVoluntaryService = activityVoluntaryService;
  }

  @GetMapping
  public ResponseEntity<List<ActivityDTO>> getAllActivities(@AuthenticationPrincipal UserDetails userDetails) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    List<ActivityDTO> activities = activityService.getAllActivities(authenticatedUserId);
    return ResponseEntity.status(HttpStatus.OK).body(activities);
  }

  @PatchMapping("/{activityId}/updateFavorite")
  public ResponseEntity<Boolean> patchFavoriteStatus(
    @PathVariable UUID activityId,
    @RequestBody UpdateFavoriteRequestDTO request,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    boolean updatedFavoriteStatus = activityService.updatedFavoriteStatus(activityId, request.isSaved(), authenticatedUserId);
    return ResponseEntity.status(HttpStatus.OK).body(updatedFavoriteStatus);
  }

  @PatchMapping("/{activityId}/updateRegistered")
  public ResponseEntity<UpdateRegisteredResponseDTO> patchRegisteredStatus(
    @PathVariable UUID activityId,
    @RequestBody UpdateRegisteredRequestDTO request,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    boolean updatedRegisterStatus = activityService.updatedRegisterStatus(activityId, request.isRegistered(), authenticatedUserId);

    // get fresh data to update frontEnd number of participants
    ActivityVoluntaryDTO activityVoluntaryDTO = activityVoluntaryService.getActivityVoluntary(activityId);

    UpdateRegisteredResponseDTO response = new UpdateRegisteredResponseDTO(updatedRegisterStatus, activityVoluntaryDTO);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
