package com.back_alasso.Address;

public record AddressResponseDTO(String houseNumber, String streetName, String zipCode, String city, String country, String displayName) {
  public static AddressResponseDTO fromEntityToDTO(Address address) {
    return new AddressResponseDTO(
      address.getHouse_number(),
      address.getStreet_name(),
      address.getZipCode(),
      address.getCity(),
      address.getCountry().getName(),
      address.getDisplayName()
    );
  }
}
