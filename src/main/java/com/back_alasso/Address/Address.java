package com.back_alasso.Address;

import com.back_alasso.Country.Country;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Address extends BaseEntity {

  public static final int STREET_MAX_LENGTH = 255;
  public static final int ZIPCODE_MAX_LENGTH = 20;
  public static final int CITY_MAX_LENGTH = 100;

  @Column(nullable = true)
  private String house_number;

  @Column(nullable = true, length = STREET_MAX_LENGTH)
  private String street_name;

  @Column(nullable = false, length = ZIPCODE_MAX_LENGTH)
  private String zipCode;

  @Column(nullable = false, length = CITY_MAX_LENGTH)
  private String city;

  @ManyToOne
  @JoinColumn(name = "country_id")
  private Country country;

  // Necessary to have an empty constructor to instance object.
  public Address() {}

  public Address(String house_number, String street_name, String zipCode, String city, Country country) {
    this.house_number = house_number;
    this.street_name = street_name;
    this.zipCode = zipCode;
    this.city = city;
    this.country = country;
  }

  public String getHouse_number() {
    return house_number;
  }

  public void setHouse_number(String house_number) {
    this.house_number = house_number;
  }

  public String getStreet_name() {
    return street_name;
  }

  public void setStreet_name(String street_name) {
    this.street_name = street_name;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }
}
