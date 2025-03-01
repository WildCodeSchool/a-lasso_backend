package com.back_alasso.AssociationFollower;

import com.back_alasso.Association.Association;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AssociationFollower extends BaseEntity {

  @Column(nullable = false)
  private boolean is_notification_active = false;

  @Column(nullable = false)
  private boolean is_follow = false;

  @ManyToOne
  @JoinColumn(name = "voluntary_id")
  private Voluntary voluntary;

  @ManyToOne
  @JoinColumn(name = "association_id")
  private Association association;

  // Necessary to have an empty constructor to instance object.
  public AssociationFollower() {}

  public AssociationFollower(boolean is_follow, boolean is_notification_active, Voluntary voluntary, Association association) {}

  public boolean isIs_notification_active() {
    return is_notification_active;
  }

  public void setIs_notification_active(boolean is_notification_active) {
    this.is_notification_active = is_notification_active;
  }

  public boolean isIs_follow() {
    return is_follow;
  }

  public void setIs_follow(boolean is_follow) {
    this.is_follow = is_follow;
  }

  public Voluntary getVoluntary() {
    return voluntary;
  }

  public void setVoluntary(Voluntary voluntary) {
    this.voluntary = voluntary;
  }

  public Association getAssociation() {
    return association;
  }

  public void setAssociation(Association association) {
    this.association = association;
  }
}
