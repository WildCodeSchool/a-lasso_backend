package com.back_alasso.Address;

import com.back_alasso.Activity.DTO.ActivitySaveRequestDTO;
import com.back_alasso.Country.Country;
import com.back_alasso.Country.CountryRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

  private final AddressRepository addressRepository;
  private final CountryRepository countryRepository;

  public AddressService(AddressRepository addressRepository, CountryRepository countryRepository) {
    this.addressRepository = addressRepository;
    this.countryRepository = countryRepository;
  }

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
