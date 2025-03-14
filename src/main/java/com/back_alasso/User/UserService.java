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
import com.back_alasso.Image.Image;
import com.back_alasso.Image.ImageEnumType;
import com.back_alasso.Image.ImageRepository;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.Voluntary.VoluntaryRepository;
import java.util.*;
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

  public UserService(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder,
    VoluntaryRepository voluntaryRepository,
    AssociationRepository associationRepository,
    ImageRepository imageRepository,
    CountryRepository countryRepository,
    AddressRepository addressRepository
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.voluntaryRepository = voluntaryRepository;
    this.associationRepository = associationRepository;
    this.imageRepository = imageRepository;
    this.countryRepository = countryRepository;
    this.addressRepository = addressRepository;
  }

  public void checkUserExists(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new EmailAlreadyUsedException("Cet email est déjà utilisé");
    }
  }

  public boolean registerVoluntary(VoluntaryRegistrationDTO voluntaryRegistrationDTO) {
    Image profileImage = imageRepository
      .findByUrl("/images/Voluntary/defaultAvatar.png")
      .orElse(imageRepository.save(new Image("/images/Voluntary/defaultAvatar.png", ImageEnumType.AVATAR)));

    Country country = countryRepository
      .findByName(voluntaryRegistrationDTO.country())
      .orElse(countryRepository.save(new Country(voluntaryRegistrationDTO.country())));

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

    voluntaryRepository.save(voluntary);
    return true;
  }

  public boolean registerAssociation(AssociationRegistrationDTO associationRegistrationDTO) {
    Country country = countryRepository
      .findByName(associationRegistrationDTO.address().getCountry().getName())
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

    associationRepository.save(association);
    return true;
  }

  public User initializeUser(String email, String password) {
    if (userRepository.existsByEmail(email)) {
      throw new EmailAlreadyUsedException("Cet email est déjà utilisé");
    }

    User user = new User();
    user.setEmail(email);
    user.setHashed_password(passwordEncoder.encode(password));
    return user;
  }

  public User findById(UUID id) {
    return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
  }
}
