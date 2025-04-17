package com.back_alasso.Association;

import com.back_alasso.Address.Address;
import com.back_alasso.AssociationFollower.AssociationFollower;
import com.back_alasso.AssociationImage.AssociationImage;
import com.back_alasso.Geolocation.Geolocatable;
import com.back_alasso.Preferences.Preferences;
import com.back_alasso.User.AccountEnumType;
import com.back_alasso.User.User;
import com.back_alasso.User.UserEnumType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Association extends User implements Geolocatable {

  public static final int DESC_MAX_LENGTH = 500;
  public static final int FOUNDER_MAX_LENGTH = 50;
  public static final int NAME_MAX_LENGTH = 50;
  public static final int SITE_URL_MAX_LENGTH = 255;

  @Column(nullable = true, length = DESC_MAX_LENGTH)
  private String description;

  @Column(nullable = true, length = FOUNDER_MAX_LENGTH)
  private String founder;

  @Column(nullable = true)
  private LocalDate foundationDate;

  @Column(nullable = false, length = NAME_MAX_LENGTH)
  private String name;

  @Column(nullable = true, length = SITE_URL_MAX_LENGTH)
  private String siteURL;

  @ManyToOne
  @JoinColumn(name = "adress_id")
  private Address address;

  @OneToMany(mappedBy = "association")
  private List<AssociationImage> associationImages;

  @OneToMany(mappedBy = "association")
  private List<AssociationFollower> associationFollowers;

  // Necessary to have an empty constructor to instance object.
  public Association() {
    super();
  }

  public Association(
    String description,
    String founder,
    LocalDate foundationDate,
    String name,
    Address address,
    List<AssociationImage> associationImages,
    Set<UserEnumType> roles,
    AccountEnumType account_status,
    String hashed_password,
    String email,
    Preferences preferences,
    String siteURL
  ) {
    super(roles, account_status, hashed_password, email, preferences);
    this.description = description;
    this.founder = founder;
    this.foundationDate = foundationDate;
    this.name = name;
    this.address = address;
    this.associationImages = associationImages;
    this.siteURL = siteURL;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getFounder() {
    return founder;
  }

  public void setFounder(String founder) {
    this.founder = founder;
  }

  public LocalDate getFoundationDate() {
    return foundationDate;
  }

  public void setFoundationDate(LocalDate foundationDate) {
    this.foundationDate = foundationDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Address getAdress() {
    return address;
  }

  public void setAdress(Address address) {
    this.address = address;
  }

  public List<AssociationImage> getAssociationImages() {
    return associationImages;
  }

  public void setAssociationImages(List<AssociationImage> associationImages) {
    this.associationImages = associationImages;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public List<AssociationFollower> getAssociationFollowers() {
    return associationFollowers;
  }

  public void setAssociationFollowers(List<AssociationFollower> associationFollowers) {
    this.associationFollowers = associationFollowers;
  }

  public String getSiteURL() {
    return siteURL;
  }

  public void setSiteURL(String siteURL) {
    this.siteURL = siteURL;
  }
}
