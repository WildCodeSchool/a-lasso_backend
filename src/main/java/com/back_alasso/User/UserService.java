package com.back_alasso.User;

import com.back_alasso.Address.Address;
import com.back_alasso.Address.AddressRepository;
import com.back_alasso.Association.Association;
import com.back_alasso.Association.AssociationRepository;
import com.back_alasso.Authentication.AssociationRegistrationDTO;
import com.back_alasso.Authentication.VoluntaryRegistrationDTO;
import com.back_alasso.Country.Country;
import com.back_alasso.Country.CountryRepository;
import com.back_alasso.Exception.EmailAlreadyUsedException;
import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.Geolocalisation.Geolocalisation;
import com.back_alasso.Geolocalisation.GeolocalisationRepository;
import com.back_alasso.Geolocalisation.GeolocalisationService;
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
  private final GeolocalisationService geolocalisationService;
  private final GeolocalisationRepository geolocalisationRepository;

  public UserService(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder,
    VoluntaryRepository voluntaryRepository,
    AssociationRepository associationRepository,
    ImageRepository imageRepository,
    CountryRepository countryRepository,
    AddressRepository addressRepository,
    PreferencesRepository preferencesRepository,
    GeolocalisationService geolocalisationService,
    GeolocalisationRepository geolocalisationRepository
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.voluntaryRepository = voluntaryRepository;
    this.associationRepository = associationRepository;
    this.imageRepository = imageRepository;
    this.countryRepository = countryRepository;
    this.addressRepository = addressRepository;
    this.preferencesRepository = preferencesRepository;
    this.geolocalisationService = geolocalisationService;
    this.geolocalisationRepository = geolocalisationRepository;
  }

  public void checkUserExists(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new EmailAlreadyUsedException("Cet email est déjà utilisé");
    }
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
      voluntaryRegistrationDTO.mobile_phone().orElse(""),
      null,
      null
    );

    Geolocalisation geolocVoluntary = geolocalisationService.getVoluntaryCoordinates(
      voluntaryRegistrationDTO.city(),
      voluntaryRegistrationDTO.country()
    );

    geolocalisationRepository.save(geolocVoluntary);
    voluntary.setGeolocalisation(geolocVoluntary);

    voluntaryRepository.save(voluntary);
    preferencesRepository.save(new Preferences(voluntary));

    return true;
  }

  public boolean registerAssociation(AssociationRegistrationDTO associationRegistrationDTO) {
    Country country = countryRepository
      .findFirstByName(associationRegistrationDTO.address().getCountry().getName())
      .orElse(countryRepository.save(new Country(associationRegistrationDTO.address().getCountry().getName())));

    Address address = addressRepository.save(
      new Address(
        associationRegistrationDTO.address().getHouse_number(),
        associationRegistrationDTO.address().getStreet_name(),
        associationRegistrationDTO.address().getAdress_suffix(),
        associationRegistrationDTO.address().getZipCode(),
        associationRegistrationDTO.address().getCity(),
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

    Geolocalisation geolocAssociation = geolocalisationService.getCoordinatesWithFullAddress(associationRegistrationDTO.address());

    geolocalisationRepository.save(geolocAssociation);
    association.setGeolocalisation(geolocAssociation);

    associationRepository.save(association);
    preferencesRepository.save(new Preferences(association));
    return true;
  }

  public User findById(UUID id) {
    return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
  }
}
