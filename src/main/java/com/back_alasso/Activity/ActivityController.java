package com.back_alasso.Activity;

import com.back_alasso.Activity.DTO.*;
import com.back_alasso.ActivityVoluntary.ActivityParticipantsRequestDTO;
import com.back_alasso.ActivityVoluntary.ActivityVoluntaryService;
import com.back_alasso.User.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private Validator validator;

  public ActivityController(ActivityService activityService, UserService userService, ActivityVoluntaryService activityVoluntaryService) {
    this.activityService = activityService;
    this.userService = userService;
    this.activityVoluntaryService = activityVoluntaryService;
  }

  @GetMapping
  public ResponseEntity<List<ActivityResponseDTO>> getAllActivities(@AuthenticationPrincipal UserDetails userDetails) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    List<ActivityResponseDTO> activities = activityService.getAllActivities(authenticatedUserId);
    return ResponseEntity.status(HttpStatus.OK).body(activities);
  }

  @GetMapping("/{activityId}")
  public ResponseEntity<ActivityResponseDTO> getActivityById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID activityId) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    ActivityResponseDTO activity = activityService.getActivityById(authenticatedUserId, activityId);
    return ResponseEntity.status(HttpStatus.OK).body(activity);
  }

  @PatchMapping("/{activityId}/updateFavorite")
  public ResponseEntity<Boolean> patchFavoriteStatus(
    @PathVariable UUID activityId,
    @Valid @RequestBody UpdateFavoriteRequestDTO request,
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
    ActivityParticipantsRequestDTO activityParticipantsRequestDTO = activityVoluntaryService.getActivityVoluntary(activityId);

    UpdateRegisteredResponseDTO response = new UpdateRegisteredResponseDTO(updatedRegisterStatus, activityParticipantsRequestDTO);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping
  public ResponseEntity<?> createOrUpdateActivity(
    @RequestBody ActivitySaveRequestDTO newActivityDTO,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    if (newActivityDTO.getStatus() == ActivityStatusEnumType.published) {
      validator.validate(newActivityDTO, OnPublish.class);
    }

        UUID userId = userService.getAuthenticatedUserId(userDetails);
        ActivityResponseDTO saved;

        saved = activityService.saveOrUpdateActivity(newActivityDTO, userId);

    return ResponseEntity.status(HttpStatus.OK).body(saved);
  }

  @DeleteMapping("/delete/{activityId}")
  public ResponseEntity<Void> deleteActivity(@PathVariable UUID activityId, @AuthenticationPrincipal UserDetails userDetails) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    activityService.deleteActivity(activityId, authenticatedUserId);
    return ResponseEntity.noContent().build();
  }
}
