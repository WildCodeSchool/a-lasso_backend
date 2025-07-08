package com.back_alasso.Activity;

import com.back_alasso.Activity.DTO.ActivityResponseDTO;
import com.back_alasso.Activity.DTO.ActivitySaveRequestDTO;
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

import org.apache.commons.lang3.StringUtils; // Use this for string null/blank checks if needed
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    // === FIELDS ===
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

    // === CONSTRUCTOR ===
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

    // === PRIVATE HELPERS ===

    private ActivityVoluntary getActivityVoluntary(UUID authenticatedUser, UUID activityId) {
        return activityVoluntaryRepository.findByVoluntary_idAndActivity_id(authenticatedUser, activityId).orElse(null);
    }

    private Voluntary getVoluntary(UUID authenticatedUser) {
        return voluntaryRepository.findById(authenticatedUser).orElseThrow(() -> new ResourceNotFoundException("Voluntary not found"));
    }

    private Association getAssociation(UUID id) {
        return associationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Association not found"));
    }

    private Address createOrRetrieveAddress(ActivitySaveRequestDTO dto) {
   
        Country country = countryRepository
                .findFirstByName(dto.getAddress().country())
                .orElseGet(() -> countryRepository.save(new Country(dto.getAddress().country())));

        Address address = new Address(
                dto.getAddress().houseNumber(),
                dto.getAddress().streetName(),
                dto.getAddress().zipCode(),
                dto.getAddress().city(),
                country,
                dto.getAddress().displayName()
        );

        return addressRepository.save(address);
    }

    private Geolocation createGeolocation(ActivitySaveRequestDTO dto) {
        if (dto.getAddress() == null || dto.getLocation().latitude() == 0 || dto.getLocation().longitude() == 0) {
            return null;
        }

        return geolocationRepository.save(new Geolocation(dto.getLocation().longitude(), dto.getLocation().latitude()));
    }


    private List<Theme> getThemes(List<ThemeNameEnumType> themeNames) {
        if (themeNames == null || themeNames.isEmpty()) {
            return Collections.emptyList();
        }

        List<Theme> themes = themeRepository.findAllByNameIn(themeNames);
        if (themes.isEmpty()) {
            throw new ResourceNotFoundException("Themes not found");
        }
        return themes;
    }

    private void linkThemesToActivity(Activity activity, List<Theme> themes) {
        if (themes == null) return;

        // Remove only ActivityThemes that are not in the new themes list
        List<ActivityTheme> toRemove = new ArrayList<>();
        for (ActivityTheme at : activity.getActivityThemes()) {
            if (themes.stream().noneMatch(theme -> theme.getId().equals(at.getTheme().getId()))) {
                toRemove.add(at);
            }
        }
        activity.getActivityThemes().removeAll(toRemove);

        // Add new ActivityThemes for themes not already linked
        for (Theme theme : themes) {
            boolean alreadyLinked = activity.getActivityThemes().stream().anyMatch(at -> at.getTheme().getId().equals(theme.getId()));
            if (!alreadyLinked) {
                activity.getActivityThemes().add(new ActivityTheme(activity, theme));
            }
        }
    }

    private List<Image> processImages(List<ImageActivityCreationRequestDTO> imageDTOs) {
        if (imageDTOs == null || imageDTOs.isEmpty()) return Collections.emptyList();

        List<Image> result = new ArrayList<>();
        List<Image> newImages = new ArrayList<>();

        for (ImageActivityCreationRequestDTO dto : imageDTOs) {
            if (dto.id() != null) {
                Image existing = imageRepository.findById(dto.id()).orElseThrow(() -> new ResourceNotFoundException("Image not found with ID: " + dto.id()));
                result.add(existing);
            } else if (StringUtils.isNotBlank(dto.base64())) {
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
        if (images == null) return;

        // Remove only ActivityImages that are not in the new images list
        List<ActivityImage> toRemove = new ArrayList<>();
        for (ActivityImage ai : activity.getActivityImages()) {
            if (images.stream().noneMatch(img -> img.getId().equals(ai.getImage().getId()))) {
                toRemove.add(ai);
            }
        }
        activity.getActivityImages().removeAll(toRemove);

        // Add new ActivityImages for images not already linked
        for (Image image : images) {
            boolean alreadyLinked = activity.getActivityImages().stream().anyMatch(ai -> ai.getImage().getId().equals(image.getId()));
            if (!alreadyLinked) {
                activity.getActivityImages().add(new ActivityImage(image, activity));
            }
        }
    }

    // === PUBLIC METHODS ===

    public Activity getActivity(UUID activityId) {
        return activityRepository.findById(activityId).orElseThrow(() -> new ResourceNotFoundException("Activity not found"));
    }

    public List<ActivityResponseDTO> getAllActivities(UUID authenticatedUserId) {
        List<Activity> activities = activityRepository.findAll();

        if (activities.isEmpty()) {
            throw new ResourceNotFoundException("activities not found");
        }

        return activities.stream().map(activity -> activityResponseMapper.fromEntityToDTO(activity, authenticatedUserId)).collect(Collectors.toList());
    }

    public ActivityResponseDTO getActivityById(UUID authenticatedUserId, UUID activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResourceNotFoundException("activity not found"));

        return activityResponseMapper.fromEntityToDTO(activity, authenticatedUserId);
    }

    @Transactional
    public ActivityResponseDTO saveOrUpdateActivity(ActivitySaveRequestDTO dto, UUID userId) {

        Association association = getAssociation(userId);

        Address address = createOrRetrieveAddress(dto);
        Geolocation geolocation = createGeolocation(dto);

        Boolean isUpdate = dto.getId() != null;

        Activity activity;
        if (isUpdate) {
            activity = activityRepository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException("Activity not found for update"));

            activity.setStatus(dto.getStatus());
            activity.setTitle(dto.getTitle());
            activity.setDate(dto.getDateTime());
            activity.setDescription(dto.getDescription());
            activity.setVoluntaries_request(dto.getRequestedVolunteers());
            activity.setAddress(address);
            activity.setGeolocation(geolocation);
        } else {
            activity = new Activity(
                    dto.getStatus(),
                    dto.getTitle(),
                    dto.getDateTime(),
                    dto.getDescription(),
                    dto.getRequestedVolunteers(),
                    association,
                    address,
                    null,
                    null
            );
            activity.setGeolocation(geolocation);
            activity = activityRepository.save(activity);
        }

        List<Theme> themes = getThemes(dto.getThemes());
        linkThemesToActivity(activity, themes);

        List<Image> images = processImages(dto.getImages());
        linkImagesToActivity(activity, images);

        return activityResponseMapper.fromEntityToDTO(activity, userId);
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
