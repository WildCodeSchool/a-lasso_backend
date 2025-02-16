package com.back_alasso.Activity;

import com.back_alasso.ActivityImage.ActivityImage;
import com.back_alasso.ActivityTheme.ActivityTheme;
import com.back_alasso.Adress.Adress;
import com.back_alasso.Association.Association;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;
import java.util.List;


@Entity
public class Activity extends BaseEntity {

  public static final int TITLE_MAX_LENGTH = 50;

  @Column(nullable = false, length = TITLE_MAX_LENGTH)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private Long volontaries_request;

  @ManyToOne
  @JoinColumn(name = "association_id")
  private Association association;

  @ManyToOne
  @JoinColumn(name = "adress_id")
  private Adress adress;

  @OneToMany(mappedBy = "activity")
  private List<ActivityImage> activityImages;

  @OneToMany(mappedBy = "activity")
  private List<ActivityTheme> activityThemes;


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getVolontaries_request() {
    return volontaries_request;
  }

  public void setVolontaries_request(Long volontaries_request) {
    this.volontaries_request = volontaries_request;
  }

  public Association getAssociation() {
    return association;
  }

  public void setAssociation(Association association) {
    this.association = association;
  }

  public Adress getAdress() {
    return adress;
  }

  public void setAdress(Adress adress) {
    this.adress = adress;
  }

  public List<ActivityImage> getActivityImages() {
    return activityImages;
  }

  public void setActivityImages(List<ActivityImage> activityImages) {
    this.activityImages = activityImages;
  }

  public List<ActivityTheme> getActivityThemes() {
    return activityThemes;
  }

  public void setActivityThemes(List<ActivityTheme> activityThemes) {
    this.activityThemes = activityThemes;
  }
}
