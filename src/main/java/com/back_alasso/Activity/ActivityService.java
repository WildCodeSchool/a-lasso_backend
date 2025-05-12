package com.back_alasso.Activity;

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
import com.back_alasso.Geolocation.GeolocationService;
import com.back_alasso.Image.Image;
import com.back_alasso.Image.ImageEnumType;
import com.back_alasso.Image.ImageRepository;
import com.back_alasso.Theme.Theme;
import com.back_alasso.Theme.ThemeRepository;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.Voluntary.VoluntaryRepository;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
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
  private final ActivityThemeRepository activityThemeRepository;
  private final AddressRepository addressRepository;
  private final GeolocationRepository geolocationRepository;
  private final GeolocationService geolocationService;
  private final ImageRepository imageRepository;
  private final ActivityImageRepository activityImageRepository;
  private final CountryRepository countryRepository;

  public ActivityService(
    ActivityRepository activityRepository,
    VoluntaryRepository voluntaryRepository,
    ActivityVoluntaryRepository activityVoluntaryRepository,
    AssociationRepository associationRepository,
    ThemeRepository themeRepository,
    ActivityThemeRepository activityThemeRepository,
    AddressRepository addressRepository,
    GeolocationRepository geolocationRepository,
    GeolocationService geolocationService,
    ImageRepository imageRepository,
    ActivityImageRepository activityImageRepository,
    CountryRepository countryRepository
  ) {
    this.activityRepository = activityRepository;
    this.voluntaryRepository = voluntaryRepository;
    this.activityVoluntaryRepository = activityVoluntaryRepository;
    this.associationRepository = associationRepository;
    this.themeRepository = themeRepository;
    this.activityThemeRepository = activityThemeRepository;
    this.addressRepository = addressRepository;
    this.geolocationRepository = geolocationRepository;
    this.geolocationService = geolocationService;
    this.imageRepository = imageRepository;
    this.activityImageRepository = activityImageRepository;
    this.countryRepository = countryRepository;
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
      .findById(authenticatedUser)
      .orElseThrow(() -> new ResourceNotFoundException("association not found"));

    List<Image> images = newActivityDTO
      .images()
      .stream()
      .map(imgDto -> {
        String base64 = imgDto.base64();
        if (base64.startsWith("data:")) {
          base64 = base64.substring(base64.indexOf(",") + 1);
        }

        byte[] imageDataBlob = Base64.getDecoder().decode(base64);
        Image image = new Image();
        image.setData(imageDataBlob);
        image.setType(ImageEnumType.ACTIVITY);
        image.setUrl("");
        return image;
      })
      .toList();

    List<Image> savedImages = imageRepository.saveAll(images);

    Optional<Country> countryFromDataBase = countryRepository.findFirstByName(newActivityDTO.country());
    Country country = countryFromDataBase.orElseGet(() -> countryRepository.save(new Country(newActivityDTO.country())));

    Address address = new Address(
      newActivityDTO.houseNumber(),
      newActivityDTO.streetName(),
      null,
      newActivityDTO.zipCode(),
      newActivityDTO.city(),
      country
    );
    Address savedAddress = addressRepository.save(address);

    Geolocation geolocation = geolocationService.getCoordinatesWithFullAddress(address);
    Geolocation savedGeolocation = geolocationRepository.save(geolocation);

    Activity newActivity = new Activity(
      newActivityDTO.title(),
      newActivityDTO.dateTime(),
      newActivityDTO.description(),
      newActivityDTO.requestedVolunteers(),
      association,
      savedAddress,
      null,
      null
    );

    newActivity.setGeolocation(savedGeolocation);

    Activity savedActivity = activityRepository.save(newActivity);

    List<Theme> themes = themeRepository.findAllByNameIn(newActivityDTO.themes());
    if (themes.isEmpty()) {
      throw new ResourceNotFoundException("Themes not found");
    }
    List<ActivityTheme> activityThemes = themes.stream().map(theme -> new ActivityTheme(savedActivity, theme)).toList();

    activityThemeRepository.saveAll(activityThemes);

    List<ActivityImage> activityImages = savedImages.stream().map(image -> new ActivityImage(image, savedActivity)).toList();

    activityImageRepository.saveAll(activityImages);

    // Reload to fetch themes and images

    // Activity savedActivityReloaded = activityRepository.findById(savedActivity.getId()).orElseThrow();

    savedActivity.setActivityThemes(activityThemes);
    savedActivity.setActivityImages(activityImages);

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
