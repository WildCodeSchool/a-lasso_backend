package com.back_alasso.features.Association;

import com.back_alasso.features.Activity.Activity;
import com.back_alasso.features.Address.Address;
import com.back_alasso.features.AssociationFollower.AssociationFollower;
import com.back_alasso.features.AssociationImage.AssociationImage;
import com.back_alasso.features.Geolocation.Geolocatable;
import com.back_alasso.features.Preferences.Preferences;
import com.back_alasso.features.Statistic.Statistic;
import com.back_alasso.features.User.AccountEnumType;
import com.back_alasso.features.User.User;
import com.back_alasso.features.User.UserEnumType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

  @OneToMany(mappedBy = "association", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AssociationImage> associationImages;

  @OneToMany(mappedBy = "association", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AssociationFollower> associationFollowers;

  @OneToMany(mappedBy = "association", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Activity> activities;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Statistic> statistics;

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
}
