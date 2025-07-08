package com.back_alasso.Address;

public record AddressRequestDTO(
        String houseNumber,
        String streetName,
        String zipCode,
        String city,
        String country,
        String displayName
) {
}
