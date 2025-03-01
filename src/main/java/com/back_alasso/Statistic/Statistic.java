package com.back_alasso.Statistic;

import com.back_alasso.User.User;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Statistic extends BaseEntity {

  public static final int DESCRIPTION_MAX_LENGTH = 100;

  @Column(nullable = false)
  private Integer value;

  @Column(nullable = false, length = DESCRIPTION_MAX_LENGTH)
  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  // Necessary to have an empty constructor to instance object.
  public Statistic() {}

  public Statistic(Integer value, String description, User user) {
    this.value = value;
    this.description = description;
    this.user = user;
  }

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
