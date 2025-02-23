package com.back_alasso.Geolocalisation;

import com.back_alasso.Activity.Activity;
import com.back_alasso.User.User;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

// TODO: revoir pour changer les coordonnées avec Point d'hibernate spacial ?

@Entity
public class Geolocalisation extends BaseEntity {

  @Column(nullable = false)
  private double longitude;

  @Column(nullable = false)
  private double latitude;

  @OneToMany(mappedBy = "geolocalisation") // one localisation can be shared by some users
  private List<User> users = new ArrayList<>();

  @OneToMany(mappedBy = "geolocalisation") // one localisation can be shared by some activities
  private List<Activity> activities = new ArrayList<>();

  // Necessary to have an empty constructor to instance object.
  public Geolocalisation() {}

  // constructor for activity or user(voluntary or association) 's localisation
  public Geolocalisation(double longitude, double latitude) {
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

  // Method to add an activity to the geolocalisation
  public void addActivity(Activity activity) {
    this.activities.add(activity);
  }

  // Method to add a user(volutary or association) to the geolocalisation
  public void addUser(User user) {
    this.users.add(user);
  }
}
