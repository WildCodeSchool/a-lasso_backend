package com.back_alasso.Address;

import com.back_alasso.Country.Country;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
