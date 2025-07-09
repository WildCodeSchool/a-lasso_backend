package com.back_alasso.Preferences;

import com.back_alasso.User.User;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Preferences extends BaseEntity {

  @Column(nullable = false)
  private boolean dark_theme_selected = false;

  @Column(nullable = false)
  private boolean push_notification_active_mail = true;

  @Column(nullable = false)
  private boolean push_notification_active_mobile = false;

  @OneToOne
  @JoinColumn(name = "user_id", unique = true)
  private User user;

  public Preferences(User user) {
    this.user = user;
  }
}
