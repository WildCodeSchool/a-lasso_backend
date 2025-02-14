package com.back_alasso.Voluntary;

import com.back_alasso.Country.Country;
import com.back_alasso.Image.Image;
import com.back_alasso.User.User;
import jakarta.persistence.*;

@Entity
public class Voluntary extends User {

  public static final int CITY_MAX_LENGTH = 200;

  @Column(nullable = false, length = CITY_MAX_LENGTH)
  private String city;

  @ManyToOne
  @JoinColumn(name = "country_id")
  private Country country;

  @ManyToOne
  @JoinColumn(name = "image_id")
  private Image avatar;
}
