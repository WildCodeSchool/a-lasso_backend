package com.back_alasso.Address;

import com.back_alasso.Country.Country;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Address extends BaseEntity {

    public static final int STREET_MAX_LENGTH = 255;
    public static final int ZIPCODE_MAX_LENGTH = 20;
    public static final int CITY_MAX_LENGTH = 100;

    @Column(nullable = false)
    private Integer house_number;

    @Column(nullable = false, length = STREET_MAX_LENGTH)
    private String street_name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private AdressSuffixEnumType adress_suffix;

    @Column(nullable = false, length = ZIPCODE_MAX_LENGTH)
    private String zipCode;

    @Column(nullable = false, length = CITY_MAX_LENGTH)
    private String city;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public Integer getHouse_number() {
        return house_number;
    }

    public void setHouse_number(Integer house_number) {
        this.house_number = house_number;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public AdressSuffixEnumType getAdress_suffix() {
        return adress_suffix;
    }

    public void setAdress_suffix(AdressSuffixEnumType adress_suffix) {
        this.adress_suffix = adress_suffix;
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
