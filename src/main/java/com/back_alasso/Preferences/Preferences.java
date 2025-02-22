package com.back_alasso.Preferences;

import com.back_alasso.User.User;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Preferences extends BaseEntity {

  @Column(nullable = false)
  private boolean is_dark_theme_selected = false;

  @Column(nullable = false)
  private boolean is_push_notification_active_mail = true;

  @Column(nullable = false)
  private boolean is_push_notification_active_mobile = false;

  @OneToOne
  @JoinColumn(name = "user_id", unique = true)
  private User user;

  // constructor for initialize preferences of a new User (Asso or Voluntary)
  // others columns is unnecessary cause have default values.
  public Preferences(User user) {
    this.user = user;
  }

  public boolean isIs_dark_theme_selected() {
    return is_dark_theme_selected;
  }

  public void setIs_dark_theme_selected(boolean is_dark_theme_selected) {
    this.is_dark_theme_selected = is_dark_theme_selected;
  }

  public boolean isIs_push_notification_active_mail() {
    return is_push_notification_active_mail;
  }

  public void setIs_push_notification_active_mail(boolean is_push_notification_active_mail) {
    this.is_push_notification_active_mail = is_push_notification_active_mail;
  }

  public boolean isIs_push_notification_active_mobile() {
    return is_push_notification_active_mobile;
  }

  public void setIs_push_notification_active_mobile(boolean is_push_notification_active_mobile) {
    this.is_push_notification_active_mobile = is_push_notification_active_mobile;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
