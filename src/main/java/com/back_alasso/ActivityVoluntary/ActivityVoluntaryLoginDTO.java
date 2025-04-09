package com.back_alasso.ActivityVoluntary;

import java.util.UUID;

public record ActivityVoluntaryLoginDTO(boolean isSaved, boolean isRegistered, UUID activityId) {
  public static ActivityVoluntaryLoginDTO fromEntityToDTO(ActivityVoluntary activityVoluntary) {
    return new ActivityVoluntaryLoginDTO(
      activityVoluntary.isIs_saved(),
      activityVoluntary.isIs_registered(),
      activityVoluntary.getActivity().getId()
    );
  }
}
