package com.back_alasso.ActivityVoluntary;

import com.back_alasso.Activity.Activity;

public record ActivityVoluntaryDTO(Long current, Long max) {
  public static ActivityVoluntaryDTO convertToDTO(Activity activity) {
    return new ActivityVoluntaryDTO(
      activity.getActivityVoluntaries() != null ? activity.getActivityVoluntaries().stream().filter(ActivityVoluntary::isIs_registered).count() : 0L,
      activity.getVolontaries_request() != null ? activity.getVolontaries_request() : 0L
    );
  }
}
