package com.back_alasso.Activity;

import com.back_alasso.Activity.DTO.ActivityCreationRequestDTO;
import com.back_alasso.Activity.DTO.ActivityResponseDTO;
import com.back_alasso.ActivityImage.ActivityImage;
import com.back_alasso.ActivityImage.ActivityImageRepository;
import com.back_alasso.ActivityTheme.ActivityTheme;
import com.back_alasso.ActivityTheme.ActivityThemeRepository;
import com.back_alasso.ActivityVoluntary.ActivityVoluntary;
import com.back_alasso.ActivityVoluntary.ActivityVoluntaryRepository;
import com.back_alasso.Address.Address;
import com.back_alasso.Address.AddressRepository;
import com.back_alasso.Association.Association;
import com.back_alasso.Association.AssociationRepository;
import com.back_alasso.Country.Country;
import com.back_alasso.Country.CountryRepository;
import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.Geolocation.Geolocation;
import com.back_alasso.Geolocation.GeolocationRepository;
import com.back_alasso.Image.DTO.ImageActivityCreationRequestDTO;
import com.back_alasso.Image.Image;
import com.back_alasso.Image.ImageEnumType;
import com.back_alasso.Image.ImageRepository;
import com.back_alasso.Theme.Theme;
import com.back_alasso.Theme.ThemeNameEnumType;
import com.back_alasso.Theme.ThemeRepository;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.Voluntary.VoluntaryRepository;
import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

  private final ActivityRepository activityRepository;
  private final VoluntaryRepository voluntaryRepository;
  private final ActivityVoluntaryRepository activityVoluntaryRepository;
  private final AssociationRepository associationRepository;
  private final ThemeRepository themeRepository;
  private final ActivityThemeRepository activityThemeRepository;
  private final AddressRepository addressRepository;
  private final GeolocationRepository geolocationRepository;
  private final ImageRepository imageRepository;
  private final ActivityImageRepository activityImageRepository;
  private final CountryRepository countryRepository;
  private final ActivityResponseMapper activityResponseMapper;

  public ActivityService(
    ActivityRepository activityRepository,
    VoluntaryRepository voluntaryRepository,
    ActivityVoluntaryRepository activityVoluntaryRepository,
    AssociationRepository associationRepository,
    ThemeRepository themeRepository,
    ActivityThemeRepository activityThemeRepository,
    AddressRepository addressRepository,
    GeolocationRepository geolocationRepository,
    ImageRepository imageRepository,
    ActivityImageRepository activityImageRepository,
    CountryRepository countryRepository,
    ActivityResponseMapper activityResponseMapper
  ) {
    this.activityRepository = activityRepository;
    this.voluntaryRepository = voluntaryRepository;
    this.activityVoluntaryRepository = activityVoluntaryRepository;
    this.associationRepository = associationRepository;
    this.themeRepository = themeRepository;
    this.activityThemeRepository = activityThemeRepository;
    this.addressRepository = addressRepository;
    this.geolocationRepository = geolocationRepository;
    this.imageRepository = imageRepository;
    this.activityImageRepository = activityImageRepository;
    this.countryRepository = countryRepository;
    this.activityResponseMapper = activityResponseMapper;
  }

  // service private shared methods

  private ActivityVoluntary getActivityVoluntary(UUID authenticatedUser, UUID activityId) {
    return activityVoluntaryRepository.findByVoluntary_idAndActivity_id(authenticatedUser, activityId).orElse(null);
  }

  private Voluntary getVoluntary(UUID authenticatedUser) {
    return voluntaryRepository.findById(authenticatedUser).orElseThrow(() -> new ResourceNotFoundException("Voluntary not found"));
  }

  private Association getAssociation(UUID id) {
    return associationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Association not found"));
  }

  private Address createOrRetrieveAddress(ActivityCreationRequestDTO dto) {
    Country country = countryRepository
      .findFirstByName(dto.address().country())
      .orElseGet(() -> countryRepository.save(new Country(dto.address().country())));

    Address address = new Address(dto.address().houseNumber(), dto.address().streetName(), dto.address().zipCode(), dto.address().city(), country);

    return addressRepository.save(address);
  }

  private Geolocation createGeolocation(ActivityCreationRequestDTO dto) {
    return geolocationRepository.save(new Geolocation(dto.address().lon(), dto.address().lat()));
  }

  private Activity createActivity(ActivityCreationRequestDTO dto, Association association, Address address, Geolocation geo) {
    Activity activity = new Activity(dto.title(), dto.dateTime(), dto.description(), dto.requestedVolunteers(), association, address, null, null);
    activity.setGeolocation(geo);
    return activityRepository.save(activity);
  }

  private List<Theme> getThemes(List<ThemeNameEnumType> themeNames) {
    List<Theme> themes = themeRepository.findAllByNameIn(themeNames);
    if (themes.isEmpty()) {
      throw new ResourceNotFoundException("Themes not found");
    }
    return themes;
  }

  private void linkThemesToActivity(Activity activity, List<Theme> themes) {
    List<ActivityTheme> activityThemes = themes.stream().map(theme -> new ActivityTheme(activity, theme)).toList();

    activityThemeRepository.saveAll(activityThemes);
    activity.setActivityThemes(activityThemes);
  }

  private List<Image> processImages(List<ImageActivityCreationRequestDTO> imageDTOs) {
    List<Image> result = new ArrayList<>();
    List<Image> newImages = new ArrayList<>();

    for (ImageActivityCreationRequestDTO dto : imageDTOs) {
      if (dto.id() != null) {
        Image existing = imageRepository.findById(dto.id()).orElseThrow(() -> new ResourceNotFoundException("Image not found with ID: " + dto.id()));
        result.add(existing);
      } else if (dto.base64() != null && !dto.base64().isEmpty()) {
        String base64 = dto.base64();
        if (base64.startsWith("data:")) {
          base64 = base64.substring(base64.indexOf(",") + 1);
        }
        byte[] imageData = Base64.getDecoder().decode(base64);

        Image image = new Image();
        image.setData(imageData);
        image.setType(ImageEnumType.ACTIVITY);
        image.setUrl("");
        newImages.add(image);
      }
    }

    List<Image> savedNewImages = imageRepository.saveAll(newImages);

    result.addAll(savedNewImages);
    return result;
  }

  private void linkImagesToActivity(Activity activity, List<Image> images) {
    List<ActivityImage> activityImages = images.stream().map(image -> new ActivityImage(image, activity)).toList();

    activityImageRepository.saveAll(activityImages);
    activity.setActivityImages(activityImages);
  }

  // service public methods

  public Activity getActivity(UUID activityId) {
    return activityRepository.findById(activityId).orElseThrow(() -> new ResourceNotFoundException("Activity not found"));
  }

  public List<ActivityResponseDTO> getAllActivities(UUID authenticatedUserId) {
    List<Activity> activities = activityRepository.findAllFromNotBannedAssociations();

    if (activities.isEmpty()) {
      throw new ResourceNotFoundException("activities not found");
    }

    return activities.stream().map(activity -> activityResponseMapper.fromEntityToDTO(activity, authenticatedUserId)).collect(Collectors.toList());
  }

  public ActivityResponseDTO getActivityById(UUID authenticatedUserId, UUID activityId) {
    Activity activity = activityRepository
      .findByIdFromNotBannedAssociation(activityId)
      .orElseThrow(() -> new ResourceNotFoundException("activity not found"));

    return activityResponseMapper.fromEntityToDTO(activity, authenticatedUserId);
  }

  public ActivityResponseDTO addNewActivity(ActivityCreationRequestDTO newActivityDTO, UUID authenticatedUser) {
    Association association = getAssociation(authenticatedUser);

    Address address = createOrRetrieveAddress(newActivityDTO);
    Geolocation geolocation = createGeolocation(newActivityDTO);

    Activity activity = createActivity(newActivityDTO, association, address, geolocation);
    List<Theme> themes = getThemes(newActivityDTO.themes());
    linkThemesToActivity(activity, themes);

    List<Image> images = processImages(newActivityDTO.images());
    linkImagesToActivity(activity, images);

    return activityResponseMapper.fromEntityToDTO(activity, authenticatedUser);
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

  @Transactional
  public void deleteActivity(UUID activityId, UUID authenticatedUserId) {
    Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResourceNotFoundException("Activity not found"));

    if (!activity.getAssociation().getId().equals(authenticatedUserId)) {
      throw new SecurityException("You are not allowed to delete this activity");
    }

    activityRepository.delete(activity);
  }
}
