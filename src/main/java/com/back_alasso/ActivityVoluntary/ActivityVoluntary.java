package com.back_alasso.ActivityVoluntary;

import com.back_alasso.Activity.Activity;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ActivityVoluntary extends BaseEntity {

  @Column(nullable = false)
  private boolean saved;

  @Column(nullable = false)
  private boolean registered;

  @ManyToOne
  @JoinColumn(name = "voluntary_id")
  private Voluntary voluntary;

  @ManyToOne
  @JoinColumn(name = "activity_id")
  private Activity activity;

  // Necessary to have an empty constructor to instance object.
  public ActivityVoluntary() {}

  public ActivityVoluntary(boolean saved, boolean registered, Voluntary voluntary, Activity activity) {
    this.saved = saved;
    this.registered = registered;
    this.voluntary = voluntary;
    this.activity = activity;
  }

  public Voluntary getVoluntary() {
    return voluntary;
  }

  public void setVoluntary(Voluntary voluntary) {
    this.voluntary = voluntary;
  }

  public Activity getActivity() {
    return activity;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public boolean isSaved() {
    return saved;
  }

  public void setSaved(boolean saved) {
    this.saved = saved;
  }

  public boolean isRegistered() {
    return registered;
  }

  public void setRegistered(boolean registered) {
    this.registered = registered;
  }
}
