package com.back_alasso.Activity;

import com.back_alasso.ActivityVoluntary.ActivityVoluntary;
import com.back_alasso.ActivityVoluntary.ActivityVoluntaryRepository;
import com.back_alasso.Address.Address;
import com.back_alasso.Address.AddressRepository;
import com.back_alasso.Association.Association;
import com.back_alasso.Association.AssociationRepository;
import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.Theme.ThemeRepository;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.Voluntary.VoluntaryRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

  private final ActivityRepository activityRepository;
  private final VoluntaryRepository voluntaryRepository;
  private final ActivityVoluntaryRepository activityVoluntaryRepository;
  private final AssociationRepository associationRepository;
  private final ThemeRepository themeRepository;
  private final AddressRepository addressRepository;

  public ActivityService(
    ActivityRepository activityRepository,
    VoluntaryRepository voluntaryRepository,
    ActivityVoluntaryRepository activityVoluntaryRepository,
    AssociationRepository associationRepository,
    ThemeRepository themeRepository,
    AddressRepository addressRepository
  ) {
    this.activityRepository = activityRepository;
    this.voluntaryRepository = voluntaryRepository;
    this.activityVoluntaryRepository = activityVoluntaryRepository;
    this.associationRepository = associationRepository;
    this.themeRepository = themeRepository;
    this.addressRepository = addressRepository;
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

  public List<ActivityDTO> getAllActivities(UUID authenticatedUserId) {
    List<Activity> activities = activityRepository.findAll();

    if (activities.isEmpty()) {
      throw new ResourceNotFoundException("activities not found");
    }

    return activities.stream().map(activity -> ActivityDTO.fromEntityToDTO(activity, authenticatedUserId)).collect(Collectors.toList());
  }

  public ActivityDTO getActivityById(UUID authenticatedUserId, UUID activityId) {
    Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResourceNotFoundException("activity not found"));
    return ActivityDTO.fromEntityToDTO(activity, authenticatedUserId);
  }

  public ActivityDTO addNewActivity(AddNewActivityDTO newActivityDTO, UUID authenticatedUser) {
    Association association = associationRepository
      .findById(newActivityDTO.associationId())
      .orElseThrow(() -> new ResourceNotFoundException("association not found"));

    Address address = new Address(null, "null", null, newActivityDTO.zipCode(), newActivityDTO.City(), null);
    addressRepository.save(address);

    Activity newActivity = new Activity(
      newActivityDTO.title(),
      LocalDateTime.parse("2025-12-22 04:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
      newActivityDTO.description(),
      newActivityDTO.volontaries_request(),
      association,
      address,
      null,
      null
    );
    Activity savedActivity = activityRepository.save(newActivity);

    //        List<Theme> themes = themeRepository.findAllByNameIn(newActivityDTO.themes()).orElseThrow(() -> new ResourceNotFoundException("association not found"));
    //
    //        List<ActivityTheme> activityThemes = themes.stream()
    //                .map(theme -> new ActivityTheme(savedActivity, theme))
    //                .collect(Collectors.toList());
    //        themeRepository.saveAll(activityThemes);

    // Images

    return ActivityDTO.fromEntityToDTO(savedActivity, authenticatedUser);
  }

  public Boolean updatedFavoriteStatus(UUID activityId, boolean isFavorite, UUID authenticatedUserId) {
    ActivityVoluntary activityVoluntary = getActivityVoluntary(authenticatedUserId, activityId);
    if (activityVoluntary != null) {
      activityVoluntary.setSaved(isFavorite);
      activityVoluntaryRepository.save(activityVoluntary);
      return isFavorite;
    } else {
      Voluntary voluntary = getVoluntary(authenticatedUserId);
      Activity activity = getActivity(activityId);
      ActivityVoluntary newActivityVoluntary = new ActivityVoluntary(isFavorite, false, voluntary, activity);
      activityVoluntaryRepository.save(newActivityVoluntary);
      return isFavorite;
    }
  }

  public Boolean updatedRegisterStatus(UUID activityId, boolean isRegistered, UUID authenticatedUserId) {
    ActivityVoluntary activityVoluntary = getActivityVoluntary(authenticatedUserId, activityId);
    if (activityVoluntary != null) {
      activityVoluntary.setRegistered(isRegistered);
      activityVoluntaryRepository.save(activityVoluntary);
      return isRegistered;
    } else {
      Voluntary voluntary = getVoluntary(authenticatedUserId);
      Activity activity = getActivity(activityId);
      ActivityVoluntary newActivityVoluntary = new ActivityVoluntary(false, isRegistered, voluntary, activity);
      activityVoluntaryRepository.save(newActivityVoluntary);
      return isRegistered;
    }
  }
}
