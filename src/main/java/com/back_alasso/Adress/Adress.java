package com.back_alasso.Adress;

import com.back_alasso.Country.Country;
import jakarta.persistence.*;

@Entity
public class Adress {

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
}
