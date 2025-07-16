package com.back_alasso.features.ActivityTheme;

import com.back_alasso.features.Activity.Activity;
import com.back_alasso.features.Theme.Theme;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ActivityThemeService {

  public void linkThemesToActivity(Activity activity, List<Theme> themes) {
    if (themes == null) return;

    // Remove only ActivityThemes that are not in the new themes list
    List<ActivityTheme> toRemove = new ArrayList<>();
    for (ActivityTheme at : activity.getActivityThemes()) {
      if (themes.stream().noneMatch(theme -> theme.getId().equals(at.getTheme().getId()))) {
        toRemove.add(at);
      }
    }
    activity.getActivityThemes().removeAll(toRemove);

    // Add new ActivityThemes for themes not already linked
    for (Theme theme : themes) {
      boolean alreadyLinked = activity.getActivityThemes().stream().anyMatch(at -> at.getTheme().getId().equals(theme.getId()));
      if (!alreadyLinked) {
        activity.getActivityThemes().add(new ActivityTheme(activity, theme));
      }
    }
  }
}
