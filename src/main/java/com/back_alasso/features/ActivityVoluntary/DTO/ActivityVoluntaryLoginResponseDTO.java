package com.back_alasso.features.ActivityVoluntary.DTO;

import com.back_alasso.features.ActivityVoluntary.ActivityVoluntary;
import java.util.UUID;

public record ActivityVoluntaryLoginResponseDTO(boolean isSaved, boolean isRegistered, UUID activityId) {
  public static ActivityVoluntaryLoginResponseDTO fromEntityToDTO(ActivityVoluntary activityVoluntary) {
    return new ActivityVoluntaryLoginResponseDTO(
      activityVoluntary.isSaved(),
      activityVoluntary.isRegistered(),
      activityVoluntary.getActivity().getId()
    );
  }
}
