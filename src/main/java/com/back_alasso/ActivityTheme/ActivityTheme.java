package com.back_alasso.ActivityTheme;

import com.back_alasso.Activity.Activity;
import com.back_alasso.Theme.Theme;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ActivityTheme extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "activity_id")
  private Activity activity;

  @ManyToOne
  @JoinColumn(name = "theme_id")
  private Theme theme;

  // Necessary to have an empty constructor to instance object.
  public ActivityTheme() {}

  public ActivityTheme(Activity activity, Theme theme) {
    this.activity = activity;
    this.theme = theme;
  }

  public Activity getActivity() {
    return activity;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public Theme getTheme() {
    return theme;
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
  }
}
