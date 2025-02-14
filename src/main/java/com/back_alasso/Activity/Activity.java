package com.back_alasso.Activity;

import com.back_alasso.ActivityImage.ActivityImage;
import com.back_alasso.Adress.Adress;
import com.back_alasso.User.User;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;
import java.util.List;
import org.hibernate.annotations.Where;

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
  @JoinColumn(name = "user_id")
  @Where(clause = "user_type = 'ASSOCIATION'")
  private User user;

  @ManyToOne
  @JoinColumn(name = "adress_id")
  private Adress adress;

  @OneToMany(mappedBy = "activity")
  private List<ActivityImage> activityImages;

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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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
}
