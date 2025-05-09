package com.back_alasso.ActivityVoluntary;

import com.back_alasso.Activity.Activity;

public record ActivityVoluntaryDTO(Long current, Long max) {
  public static ActivityVoluntaryDTO convertToDTO(Activity activity) {
    return new ActivityVoluntaryDTO(
      activity.getActivityVoluntaries() != null ? activity.getActivityVoluntaries().stream().filter(ActivityVoluntary::isRegistered).count() : 0L,
      activity.getVoluntaries_request() != null ? activity.getVoluntaries_request() : 0L
    );
  }
}
