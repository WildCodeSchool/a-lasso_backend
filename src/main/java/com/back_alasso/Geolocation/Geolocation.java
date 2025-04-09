package com.back_alasso.Geolocation;

import com.back_alasso.Activity.Activity;
import com.back_alasso.User.User;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Geolocation extends BaseEntity {

  @Column(nullable = false)
  private double longitude;

  @Column(nullable = false)
  private double latitude;

  @OneToMany(mappedBy = "geolocation") // one localisation can be shared by some users
  private List<User> users = new ArrayList<>();

  @OneToMany(mappedBy = "geolocation") // one localisation can be shared by some activities
  private List<Activity> activities = new ArrayList<>();

  // Necessary to have an empty constructor to instance object.
  public Geolocation() {}

  // constructor for activity or user(voluntary or association) 's localisation
  public Geolocation(double longitude, double latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public List<Activity> getActivities() {
    return activities;
  }

  public void setActivities(List<Activity> activities) {
    this.activities = activities;
  }
}
