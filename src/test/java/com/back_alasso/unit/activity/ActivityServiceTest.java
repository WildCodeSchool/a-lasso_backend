package com.back_alasso.unit.activity;

import com.back_alasso.Activity.ActivityResponseMapper;
import com.back_alasso.ActivityImage.ActivityImageRepository;
import com.back_alasso.ActivityTheme.ActivityThemeRepository;
import com.back_alasso.ActivityVoluntary.ActivityVoluntaryRepository;
import com.back_alasso.Address.AddressRepository;
import com.back_alasso.Association.Association;
import com.back_alasso.Activity.Activity;
import com.back_alasso.Activity.ActivityService;
import com.back_alasso.Activity.ActivityRepository;
import com.back_alasso.Address.Address;
import com.back_alasso.Association.AssociationRepository;
import com.back_alasso.Country.Country;
import com.back_alasso.Country.CountryRepository;
import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.Geolocation.GeolocationRepository;
import com.back_alasso.Image.ImageRepository;
import com.back_alasso.Theme.ThemeRepository;
import com.back_alasso.User.AccountEnumType;
import com.back_alasso.User.UserEnumType;
import com.back_alasso.Voluntary.VoluntaryRepository;

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

import com.back_alasso.Activity.DTO.ActivityStatusEnumType;

public class ActivityServiceTest {

    ActivityService activityService;

    ActivityRepository activityRepositoryMock = Mockito.mock(ActivityRepository.class);
    VoluntaryRepository voluntaryRepository = Mockito.mock(VoluntaryRepository.class);
    ActivityVoluntaryRepository activityVoluntaryRepository = Mockito.mock(ActivityVoluntaryRepository.class);
    AssociationRepository associationRepository = Mockito.mock(AssociationRepository.class);
    ThemeRepository themeRepository = Mockito.mock(ThemeRepository.class);
    ActivityThemeRepository activityThemeRepository = Mockito.mock(ActivityThemeRepository.class);
    AddressRepository addressRepository = Mockito.mock(AddressRepository.class);
    GeolocationRepository geolocationRepository = Mockito.mock(GeolocationRepository.class);
    ImageRepository imageRepository = Mockito.mock(ImageRepository.class);
    ActivityImageRepository activityImageRepository = Mockito.mock(ActivityImageRepository.class);
    CountryRepository countryRepository = Mockito.mock(CountryRepository.class);
    ActivityResponseMapper activityResponseMapper = Mockito.mock(ActivityResponseMapper.class);

    Address associationAddress = new Address(FIRST_HOUSE_NUMBER, "rue d'Athènes", "44300", "NANTES", new Country("France"),"");

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
                activityRepositoryMock,
                voluntaryRepository,
                activityVoluntaryRepository,
                associationRepository,
                themeRepository,
                activityThemeRepository,
                addressRepository,
                geolocationRepository,
                imageRepository,
                activityImageRepository,
                countryRepository,
                activityResponseMapper);
    }

    @Test
    public void shouldReturnAnException() {
        when(activityRepositoryMock.findById(activityIdMock)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            activityService.getActivity(activityIdMock);
        });
    }

    @Test
    public void shouldReturnAnActivityDTO() {
        when(activityRepositoryMock.findById(activityIdMock)).thenReturn(Optional.ofNullable(activityMock));

        Activity result = activityService.getActivity(activityIdMock);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Test title Activity", result.getTitle());
    }
}
