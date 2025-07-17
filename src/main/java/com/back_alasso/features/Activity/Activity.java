package com.back_alasso.features.Activity;

import com.back_alasso.core.BaseEntity;
import com.back_alasso.features.Activity.DTO.ActivityStatusEnumType;
import com.back_alasso.features.ActivityImage.ActivityImage;
import com.back_alasso.features.ActivityTheme.ActivityTheme;
import com.back_alasso.features.ActivityVoluntary.ActivityVoluntary;
import com.back_alasso.features.Address.Address;
import com.back_alasso.features.Association.Association;
import com.back_alasso.features.Geolocation.Geolocatable;
import com.back_alasso.features.Geolocation.Geolocation;
import com.back_alasso.features.Message.Message;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity extends BaseEntity implements Geolocatable {

  public static final int TITLE_MAX_LENGTH = 50;
  public static final int DESC_MAX_LENGTH = 1000;

  @Column
  private ActivityStatusEnumType status;

  @Column(length = TITLE_MAX_LENGTH)
  private String title;

  @Column
  private LocalDateTime date;

  @Column(length = DESC_MAX_LENGTH)
  private String description;

  @Column(nullable = false)
  private Long voluntariesRequest;

  @ManyToOne
  @JoinColumn(name = "association_id")
  private Association association;

  @ManyToOne
  @JoinColumn(name = "adress_id")
  private Address address;

  @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ActivityImage> activityImages;

  @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ActivityTheme> activityThemes;

  @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ActivityVoluntary> activityVoluntaries;

  @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Message> messages;

  @ManyToOne
  @JoinColumn(name = "geolocation_id")
  private Geolocation geolocation;

  public Activity(
    ActivityStatusEnumType status,
    String title,
    LocalDateTime date,
    String description,
    Long voluntariesRequest,
    Association association,
    Address address,
    List<ActivityImage> activityImages,
    List<ActivityTheme> activityThemes
  ) {
    this.status = status;
    this.title = title;
    this.date = date;
    this.description = description;
    this.voluntariesRequest = voluntariesRequest;
    this.association = association;
    this.address = address;
    this.activityImages = activityImages != null ? activityImages : new ArrayList<>();
    this.activityThemes = activityThemes != null ? activityThemes : new ArrayList<>();
  }

  @Override
  public Address getAddress() {
    return this.address;
  }

  @Override
  public Geolocation getGeolocation() {
    return this.geolocation;
  }
}
