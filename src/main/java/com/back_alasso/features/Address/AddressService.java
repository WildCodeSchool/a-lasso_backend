package com.back_alasso.features.Address;

import com.back_alasso.features.Activity.DTO.ActivitySaveRequestDTO;
import com.back_alasso.features.Country.Country;
import com.back_alasso.features.Country.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

  private final AddressRepository addressRepository;
  private final CountryRepository countryRepository;

  public Address createOrRetrieveAddress(ActivitySaveRequestDTO dto) {
    Country country = countryRepository
      .findFirstByName(dto.getAddress().country())
      .orElseGet(() -> countryRepository.save(new Country(dto.getAddress().country())));

    Address address = new Address(
      dto.getAddress().houseNumber(),
      dto.getAddress().streetName(),
      dto.getAddress().zipCode(),
      dto.getAddress().city(),
      dto.getAddress().displayName(),
      country
    );

    return addressRepository.save(address);
  }
}
