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
  private boolean is_saved;

  @Column(nullable = false)
  private boolean is_registered;

  @ManyToOne
  @JoinColumn(name = "voluntary_id")
  private Voluntary voluntary;

  @ManyToOne
  @JoinColumn(name = "activity_id")
  private Activity activity;

  // Necessary to have an empty constructor to instance object.
  public ActivityVoluntary() {}

  public ActivityVoluntary(boolean is_saved, boolean is_registered, Voluntary voluntary, Activity activity) {
    this.is_saved = is_saved;
    this.is_registered = is_registered;
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

  public boolean isIs_saved() {
    return is_saved;
  }

  public void setIs_saved(boolean is_saved) {
    this.is_saved = is_saved;
  }

  public boolean isIs_registered() {
    return is_registered;
  }

  public void setIs_registered(boolean is_registered) {
    this.is_registered = is_registered;
  }
}
