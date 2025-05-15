package com.back_alasso.Address;

public record AddressRequestDTO(String houseNumber, String streetName, String zipCode, String city, String country, Double lat, Double lon) {}
