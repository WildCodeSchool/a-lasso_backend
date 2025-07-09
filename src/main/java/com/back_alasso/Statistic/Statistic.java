package com.back_alasso.Statistic;

import com.back_alasso.User.User;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Statistic extends BaseEntity {

  public static final int DESCRIPTION_MAX_LENGTH = 100;

  @Column(nullable = false)
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
