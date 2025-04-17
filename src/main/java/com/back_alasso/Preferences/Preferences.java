package com.back_alasso.Preferences;

import com.back_alasso.User.User;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;

@Entity
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

  // constructor for initialize preferences of a new User (Asso or Voluntary)
  // others columns is unnecessary cause have default values.
  public Preferences(User user) {
    this.user = user;
  }

  // Necessary to have an empty constructor to instance object.
  public Preferences() {}

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public boolean isDark_theme_selected() {
    return dark_theme_selected;
  }

  public void setDark_theme_selected(boolean dark_theme_selected) {
    this.dark_theme_selected = dark_theme_selected;
  }

  public boolean isPush_notification_active_mail() {
    return push_notification_active_mail;
  }

  public void setPush_notification_active_mail(boolean push_notification_active_mail) {
    this.push_notification_active_mail = push_notification_active_mail;
  }

  public boolean isPush_notification_active_mobile() {
    return push_notification_active_mobile;
  }

  public void setPush_notification_active_mobile(boolean push_notification_active_mobile) {
    this.push_notification_active_mobile = push_notification_active_mobile;
  }
}
