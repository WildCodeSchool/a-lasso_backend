package com.back_alasso.features.Statistic;

import com.back_alasso.core.BaseEntity;
import com.back_alasso.features.User.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "statistics")
public class Statistic extends BaseEntity {

  public static final int DESCRIPTION_MAX_LENGTH = 100;

  @Column(name = "stat_value", nullable = false)
  private Integer value;

  @Column(nullable = false, length = DESCRIPTION_MAX_LENGTH)
  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Statistic(Integer value, String description, User user) {
    this.value = value;
    this.description = description;
    this.user = user;
  }
}
