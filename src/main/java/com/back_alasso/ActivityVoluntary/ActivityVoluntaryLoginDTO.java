package com.back_alasso.ActivityVoluntary;

import java.util.UUID;

public record ActivityVoluntaryLoginDTO(boolean isSaved, boolean isRegistered, UUID activityId) {
  public static ActivityVoluntaryLoginDTO fromEntityToDTO(ActivityVoluntary activityVoluntary) {
    return new ActivityVoluntaryLoginDTO(activityVoluntary.isSaved(), activityVoluntary.isRegistered(), activityVoluntary.getActivity().getId());
  }
}
