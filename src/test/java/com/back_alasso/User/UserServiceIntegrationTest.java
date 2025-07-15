package com.back_alasso.User;

import com.back_alasso.ActivityImage.ActivityImageRepository;
import com.back_alasso.AssociationImage.AssociationImageRepository;
import com.back_alasso.Authentication.DTO.VoluntaryRegistrationDTO;
import com.back_alasso.Country.CountryRepository;
import com.back_alasso.Geolocation.Geolocation;
import com.back_alasso.Geolocation.DTO.GeolocationRequestDTO;
import com.back_alasso.Geolocation.GeolocationRepository;
import com.back_alasso.Geolocation.GeolocationService;
import com.back_alasso.Image.ImageRepository;
import com.back_alasso.Preferences.Preferences;
import com.back_alasso.Preferences.PreferencesRepository;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.Voluntary.VoluntaryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static com.back_alasso.config.DatabaseInitializer.NUMBER_FOURTEEN;
import static com.back_alasso.config.DatabaseInitializer.THIRD_ASSO_YEAR_FOUNDED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoluntaryRepository voluntaryRepository;

    @Autowired
    private AssociationImageRepository associationImageRepository;

    @Autowired
    private ActivityImageRepository activityImageRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private GeolocationRepository geolocationRepository;

    @Autowired
    private PreferencesRepository preferencesRepository;

    @MockitoBean
    private GeolocationService geolocationService; // Mock du service externe

    @Test
    @DisplayName("Should register voluntary with all dependencies and save to database")
    void registerVoluntary_shouldSaveVoluntaryAndAllRelatedEntities() {

        VoluntaryRegistrationDTO registrationDTO = new VoluntaryRegistrationDTO(
                "John",
                "Doe",
                "john.doe@example.com",
                "Password123!",
                "+33123456789",
                "Paris",
                "France",
                LocalDate.of(THIRD_ASSO_YEAR_FOUNDED, Month.MARCH, NUMBER_FOURTEEN)
        );

        GeolocationRequestDTO mockGeolocation = new GeolocationRequestDTO(2.3522, 48.8566);

        when(geolocationService.getVoluntaryCoordinates("Paris", "France")).thenReturn(mockGeolocation);

        assertTrue(imageRepository.findFirstByUrl("/images/Voluntary/defaultAvatar.png").isPresent());
        assertTrue(countryRepository.findFirstByName("France").isPresent());

        boolean result = userService.registerVoluntary(registrationDTO);

        assertTrue(result);

        Optional<User> savedVoluntary = userRepository.findByEmail("john.doe@example.com");
        assertTrue(savedVoluntary.isPresent());

        Optional<Voluntary> voluntary = voluntaryRepository.findById(savedVoluntary.get().getId());

        assertEquals("john.doe@example.com", voluntary.get().getEmail());
        assertEquals("John", voluntary.get().getFirst_name());
        assertEquals("Doe", voluntary.get().getLast_name());
        assertEquals("Paris", voluntary.get().getCity());
        assertEquals("+33123456789", voluntary.get().getMobile_phone());
        assertEquals(AccountEnumType.ACTIVE, voluntary.get().getAccount_status());
        assertTrue(voluntary.get().getRoles().contains(UserEnumType.ROLE_VOLUNTARY));

        assertNotEquals("Password123!", voluntary.get().getPassword());
        assertTrue(voluntary.get().getPassword().startsWith("$2a$"));

        assertNotNull(voluntary.get().getCountry());
        assertEquals("France", voluntary.get().getCountry().getName());

        assertNotNull(voluntary.get().getAvatar());
        assertEquals("/images/Voluntary/defaultAvatar.png", voluntary.get().getAvatar().getUrl());

        assertNotNull(voluntary.get().getGeolocation());
        assertEquals(48.8566, voluntary.get().getGeolocation().getLatitude());
        assertEquals(2.3522, voluntary.get().getGeolocation().getLongitude());

        final double lat = 48.8566, lon = 2.3522, eps = 0.0001;

        List<Geolocation> geolocations = geolocationRepository.findAll();
        assertTrue(geolocations.stream().anyMatch(g ->
                Math.abs(g.getLatitude() - lat) < eps && Math.abs(g.getLongitude() - lon) < eps
        ));

        List<Preferences> preferences = preferencesRepository.findAll();
        assertTrue(preferences.stream().anyMatch(p ->
                p.getUser().getId().equals(voluntary.get().getId())));

        verify(geolocationService).getVoluntaryCoordinates("Paris", "France");
    }

    @Test
    @DisplayName("Should throw exception when country not found")
    void registerVoluntary_shouldThrowExceptionWhenCountryNotFound() {
        VoluntaryRegistrationDTO registrationDTO = new VoluntaryRegistrationDTO(
                "John",
                "Doe",
                "john.doe@example.com",
                "Password123!",
                "+33123456789",
                "Paris",
                "UnknownCountry",
                LocalDate.of(THIRD_ASSO_YEAR_FOUNDED, Month.MARCH, NUMBER_FOURTEEN)
        );

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.registerVoluntary(registrationDTO));

        assertEquals("Pays non trouvé", exception.getMessage());

        Optional<User> voluntary = userRepository.findByEmail("john.doe@example.com");
        assertFalse(voluntary.isPresent());
    }

    @Test
    @DisplayName("Should handle geolocation service failure gracefully")
    void registerVoluntary_shouldHandleGeolocationServiceFailure() {

        VoluntaryRegistrationDTO registrationDTO = new VoluntaryRegistrationDTO(
                "John",
                "Doe",
                "john.doe@example.com",
                "Password123!",
                "+33123456789",
                "Paris",
                "France",
                LocalDate.of(THIRD_ASSO_YEAR_FOUNDED, Month.MARCH, NUMBER_FOURTEEN)
        );

        when(geolocationService.getVoluntaryCoordinates("Paris", "France"))
                .thenThrow(new RuntimeException("Geolocation service unavailable"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.registerVoluntary(registrationDTO));

        assertEquals("Geolocation service unavailable", exception.getMessage());
    }
}