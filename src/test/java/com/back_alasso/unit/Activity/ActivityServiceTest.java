package com.back_alasso.unit.Activity;

import com.back_alasso.features.Activity.ActivityResponseMapper;
import com.back_alasso.features.ActivityImage.ActivityImageService;
import com.back_alasso.features.ActivityTheme.ActivityThemeService;
import com.back_alasso.features.Address.AddressService;
import com.back_alasso.features.Association.Association;
import com.back_alasso.features.Activity.Activity;
import com.back_alasso.features.Activity.ActivityService;
import com.back_alasso.features.Activity.ActivityRepository;
import com.back_alasso.features.Address.Address;
import com.back_alasso.features.Association.AssociationService;
import com.back_alasso.features.Country.Country;

import com.back_alasso.exception.ResourceNotFoundException;
import com.back_alasso.features.Geolocation.GeolocationService;
import com.back_alasso.features.Image.ImageService;
import com.back_alasso.features.Theme.ThemeService;
import com.back_alasso.features.User.AccountEnumType;
import com.back_alasso.features.User.UserEnumType;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.back_alasso.config.DatabaseInitializer.*;

import static org.mockito.Mockito.when;

import com.back_alasso.features.Activity.DTO.ActivityStatusEnumType;

public class ActivityServiceTest {

    ActivityService activityService;

    ActivityRepository activityRepository = Mockito.mock(ActivityRepository.class);
    ActivityResponseMapper activityResponseMapper = Mockito.mock(ActivityResponseMapper.class);
    AddressService addressService = Mockito.mock(AddressService.class);
    ImageService imageService = Mockito.mock(ImageService.class);
    ActivityImageService activityImageService = Mockito.mock(ActivityImageService.class);
    ThemeService themeService = Mockito.mock(ThemeService.class);
    ActivityThemeService activityThemeService = Mockito.mock(ActivityThemeService.class);
    GeolocationService geolocationService = Mockito.mock(GeolocationService.class);
    AssociationService associationService = Mockito.mock(AssociationService.class);

    Address associationAddress = new Address(FIRST_HOUSE_NUMBER, "rue d'Athènes", "44300", "NANTES", "", new Country("France"));

    Association associationMock = new Association(
            "La Croix-Rouge française agit pour protéger et relever sans condition, les personnes en situation de vulnérabilité et construire avec elles leur résilience.",
            "Henry DUNANT",
            LocalDate.of(FIRST_ASSO_YEAR_FOUNDED, FIRST_ASSO_MONTH_FOUNDED, FIRST_ASSO_DAY_FOUNDED),
            "La Croix Rouge",
            associationAddress,
            null,
            new HashSet<>(List.of(UserEnumType.ROLE_ASSOCIATION)),
            AccountEnumType.ACTIVE,
            "$2a$10$jw6BeI/txUaC1BQNGYZn4.hs5wpmLhe2uYpTBB40oUveFE3ZRQYQq",
            "lacroixrouge@gmail.com",
            null,
            "https://www.croix-rouge.fr/"
    );

    Activity activityMock = new Activity(
            ActivityStatusEnumType.published,
            "Test title Activity",
            LocalDateTime.parse("2025-12-22 04:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
            "description test",
            FIRST_ACTIVITY_VOLONTARY_REQUEST,
            associationMock,
            associationAddress,
            null,
            null
    );

    UUID activityIdMock = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    @BeforeEach
    public void setUp() {
        this.activityService = new ActivityService(
                activityRepository,
                activityResponseMapper,
                addressService,
                imageService,
                activityImageService,
                themeService,
                activityThemeService,
                geolocationService,
                associationService)
        ;
    }

    @Test
    public void shouldReturnAnException() {

        when(activityRepository.findById(activityIdMock)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            activityService.getActivityById(activityIdMock);
        });
    }

    @Test
    public void shouldReturnAnActivity() {
        activityMock.setId(activityIdMock);
        when(activityRepository.findById(activityIdMock)).thenReturn(Optional.of(activityMock));

        Activity result = activityService.getActivityById(activityIdMock);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(activityIdMock, result.getId());
        Assertions.assertEquals("Test title Activity", result.getTitle());
    }

}
