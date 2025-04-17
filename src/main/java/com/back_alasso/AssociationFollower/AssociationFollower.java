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
  private boolean notification_active = false;

  @Column(nullable = false)
  private boolean follow = false;

  @ManyToOne
  @JoinColumn(name = "voluntary_id")
  private Voluntary voluntary;

  @ManyToOne
  @JoinColumn(name = "association_id")
  private Association association;

  // Necessary to have an empty constructor to instance object.
  public AssociationFollower() {}

  public AssociationFollower(boolean notification_active, boolean follow, Voluntary voluntary, Association association) {
    this.notification_active = notification_active;
    this.follow = follow;
    this.voluntary = voluntary;
    this.association = association;
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

  public boolean isNotification_active() {
    return notification_active;
  }

  public void setNotification_active(boolean notification_active) {
    this.notification_active = notification_active;
  }

  public boolean isFollow() {
    return follow;
  }

  public void setFollow(boolean follow) {
    this.follow = follow;
  }
}
