package com.back_alasso.features.Voluntary;

import com.back_alasso.exception.ResourceNotFoundException;
import com.back_alasso.features.Country.Country;
import com.back_alasso.features.Country.CountryRepository;
import com.back_alasso.features.Geolocation.DTO.GeolocationRequestDTO;
import com.back_alasso.features.Geolocation.Geolocation;
import com.back_alasso.features.Geolocation.GeolocationRepository;
import com.back_alasso.features.Geolocation.GeolocationService;
import com.back_alasso.features.Image.Image;
import com.back_alasso.features.Image.ImageEnumType;
import com.back_alasso.features.Image.ImageService;
import com.back_alasso.features.Voluntary.DTO.VoluntaryUpdateRequestDTO;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VoluntaryService {

  private final VoluntaryRepository voluntaryRepository;
  private final ImageService imageService;
  private final GeolocationRepository geolocationRepository;
  private final GeolocationService geolocationService;
  private final CountryRepository countryRepository;

  public VoluntaryService(
    VoluntaryRepository voluntaryRepository,
    ImageService imageService,
    GeolocationRepository geolocationRepository,
    GeolocationService geolocationService,
    CountryRepository countryRepository
  ) {
    this.voluntaryRepository = voluntaryRepository;
    this.imageService = imageService;
    this.geolocationRepository = geolocationRepository;
    this.geolocationService = geolocationService;
    this.countryRepository = countryRepository;
  }

  public Voluntary findById(UUID id) {
    return voluntaryRepository.findById(id).orElse(null);
  }

  public Voluntary getVoluntaryById(UUID authenticatedUser) {
    return voluntaryRepository.findById(authenticatedUser).orElseThrow(() -> new ResourceNotFoundException("Voluntary not found"));
  }

  public void updateVoluntary(UUID voluntaryId, VoluntaryUpdateRequestDTO dto) {
    Voluntary voluntary = voluntaryRepository.findById(voluntaryId).orElseThrow();
    Country country = countryRepository.findFirstByName(dto.country()).orElseGet(() -> countryRepository.save(new Country(dto.country())));

    voluntary.setFirst_name(dto.first_name());
    voluntary.setLast_name(dto.last_name());
    voluntary.setBirth_date(dto.birth_date());
    voluntary.setCity(dto.city());
    voluntary.setCountry(country);
    voluntary.setMobile_phone(dto.mobile_phone());

    GeolocationRequestDTO newGeolocation = geolocationService.getVoluntaryCoordinates(dto.city(), dto.country());
    Geolocation geolocVoluntary = new Geolocation(newGeolocation.longitude(), newGeolocation.latitude());

    geolocationRepository.save(geolocVoluntary);
    voluntary.setGeolocation(geolocVoluntary);

    voluntaryRepository.save(voluntary);
  }

  public void uploadAvatar(UUID voluntaryId, MultipartFile avatarFile) {
    Voluntary voluntary = voluntaryRepository.findById(voluntaryId).orElseThrow();
    Image avatar = imageService.uploadImage(avatarFile, ImageEnumType.AVATAR, "Voluntary");
    voluntary.setAvatar(avatar);
    voluntaryRepository.save(voluntary);
  }
}
