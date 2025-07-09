package com.back_alasso.User;

import com.back_alasso.Address.Address;
import com.back_alasso.Address.AddressRepository;
import com.back_alasso.Association.Association;
import com.back_alasso.Association.AssociationRepository;
import com.back_alasso.AssociationImage.AssociationImage;
import com.back_alasso.AssociationImage.AssociationImageRepository;
import com.back_alasso.Authentication.DTO.AssociationRegistrationDTO;
import com.back_alasso.Authentication.DTO.VoluntaryRegistrationDTO;
import com.back_alasso.Country.Country;
import com.back_alasso.Country.CountryRepository;
import com.back_alasso.Exception.EmailAlreadyUsedException;
import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.Geolocation.Geolocation;
import com.back_alasso.Geolocation.GeolocationRepository;
import com.back_alasso.Geolocation.GeolocationService;
import com.back_alasso.Image.Image;
import com.back_alasso.Image.ImageRepository;
import com.back_alasso.Preferences.Preferences;
import com.back_alasso.Preferences.PreferencesRepository;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.Voluntary.VoluntaryRepository;
import java.util.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final VoluntaryRepository voluntaryRepository;
  private final AssociationRepository associationRepository;
  private final ImageRepository imageRepository;
  private final CountryRepository countryRepository;
  private final AddressRepository addressRepository;
  private final PreferencesRepository preferencesRepository;
  private final GeolocationService geolocationService;
  private final GeolocationRepository geolocationRepository;
  private final AssociationImageRepository associationImageRepository;

  public UserService(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder,
    VoluntaryRepository voluntaryRepository,
    AssociationRepository associationRepository,
    ImageRepository imageRepository,
    CountryRepository countryRepository,
    AddressRepository addressRepository,
    PreferencesRepository preferencesRepository,
    GeolocationService geolocationService,
    GeolocationRepository geolocationRepository,
    AssociationImageRepository associationImageRepository
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.voluntaryRepository = voluntaryRepository;
    this.associationRepository = associationRepository;
    this.imageRepository = imageRepository;
    this.countryRepository = countryRepository;
    this.addressRepository = addressRepository;
    this.preferencesRepository = preferencesRepository;
    this.geolocationService = geolocationService;
    this.geolocationRepository = geolocationRepository;
    this.associationImageRepository = associationImageRepository;
  }

  public void checkUserExists(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new EmailAlreadyUsedException("Cet email est déjà utilisé");
    }
  }

  public void changePassword(String email, String oldPassword, String newPassword) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
      throw new IllegalArgumentException("Identifiants incorrects");
    }

    user.setHashed_password(passwordEncoder.encode(newPassword));
    userRepository.save(user);
  }

  public void deleteUser(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
    preferencesRepository.delete(user.getPreferences());
    userRepository.delete(user);
  }

  public UUID getAuthenticatedUserId(UserDetails userDetails) {
    if (userDetails == null) {
      return null;
    }
    String authenticatedUserEmail = userDetails.getUsername();
    UUID authenticatedUserId = findByEmail(authenticatedUserEmail).getId();
    return authenticatedUserId;
  }

  public boolean registerVoluntary(VoluntaryRegistrationDTO voluntaryRegistrationDTO) {
    Image profileImage = imageRepository
      .findFirstByUrl("/images/Voluntary/defaultAvatar.png")
      .orElseThrow(() -> new RuntimeException("Image non trouvé"));

    Country country = countryRepository
      .findFirstByName(voluntaryRegistrationDTO.country())
      .orElseThrow(() -> new RuntimeException("Pays non trouvé"));

    Voluntary voluntary = new Voluntary(
      new HashSet<>(List.of(UserEnumType.ROLE_VOLUNTARY)),
      AccountEnumType.ACTIVE,
      passwordEncoder.encode(voluntaryRegistrationDTO.password()),
      voluntaryRegistrationDTO.email(),
      voluntaryRegistrationDTO.city(),
      country,
      voluntaryRegistrationDTO.first_name(),
      voluntaryRegistrationDTO.last_name(),
      profileImage,
      voluntaryRegistrationDTO.mobile_phone(),
      null,
      null
    );

    Geolocation geolocVoluntary = geolocationService.getVoluntaryCoordinates(voluntaryRegistrationDTO.city(), voluntaryRegistrationDTO.country());

    geolocationRepository.save(geolocVoluntary);
    voluntary.setGeolocation(geolocVoluntary);

    voluntaryRepository.save(voluntary);
    preferencesRepository.save(new Preferences(voluntary));

    return true;
  }

  public boolean registerAssociation(AssociationRegistrationDTO associationRegistrationDTO) {
    Image logoImage = imageRepository
      .findFirstByUrl("/images/Association/defaultAvatar.png")
      .orElseThrow(() -> new RuntimeException("Image non trouvé"));

    Image profileImage = imageRepository
      .findFirstByUrl("/images/Association/defaultAssociationProfileImage.png")
      .orElseThrow(() -> new RuntimeException("Image non trouvé"));

    Country country = countryRepository
      .findFirstByName(associationRegistrationDTO.address().country())
      .orElse(countryRepository.save(new Country(associationRegistrationDTO.address().country())));

    Address address = addressRepository.save(
      new Address(
        associationRegistrationDTO.address().houseNumber(),
        associationRegistrationDTO.address().streetName(),
        associationRegistrationDTO.address().zipCode(),
        associationRegistrationDTO.address().city(),
        country
      )
    );

    Association association = new Association(
      "",
      "",
      null,
      associationRegistrationDTO.name(),
      address,
      null,
      new HashSet<>(List.of(UserEnumType.ROLE_ASSOCIATION)),
      AccountEnumType.ACTIVE,
      passwordEncoder.encode(associationRegistrationDTO.password()),
      associationRegistrationDTO.email(),
      null,
      null
    );

    Geolocation geolocAssociation = new Geolocation(associationRegistrationDTO.address().lon(), associationRegistrationDTO.address().lat());

    geolocationRepository.save(geolocAssociation);
    association.setGeolocation(geolocAssociation);

    Association createdAssociation = associationRepository.save(association);
    preferencesRepository.save(new Preferences(association));

    List<AssociationImage> associationImages = Arrays.asList(
      new AssociationImage(logoImage, createdAssociation),
      new AssociationImage(profileImage, createdAssociation)
    );

    associationImageRepository.saveAll(associationImages);
    return true;
  }

  public User findById(UUID id) {
    return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
  }
}
