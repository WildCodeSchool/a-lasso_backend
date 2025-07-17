package com.back_alasso.features.Geolocation;

import com.back_alasso.core.BaseEntity;
import com.back_alasso.features.Activity.Activity;
import com.back_alasso.features.User.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Geolocation extends BaseEntity {

  @Column(nullable = false)
  private double longitude;

  @Column(nullable = false)
  private double latitude;

  @OneToMany(mappedBy = "geolocation")
  private List<User> users = new ArrayList<>();

  @OneToMany(mappedBy = "geolocation")
  private List<Activity> activities = new ArrayList<>();

  // Constructor for activity or user (voluntary or association) localisation
  public Geolocation(double longitude, double latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }
}
