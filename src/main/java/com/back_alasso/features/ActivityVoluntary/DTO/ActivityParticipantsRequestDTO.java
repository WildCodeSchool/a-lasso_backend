package com.back_alasso.features.ActivityVoluntary.DTO;

import com.back_alasso.features.Activity.Activity;
import com.back_alasso.features.ActivityVoluntary.ActivityVoluntary;
import jakarta.validation.constraints.NotNull;

public record ActivityParticipantsRequestDTO(
  @NotNull(message = "Le nombre de participants actuel est nécessaire") Long current,
  @NotNull(message = "Le nombre de participants max est nécessaire") Long max
) {
  public static ActivityParticipantsRequestDTO convertToDTO(Activity activity) {
    return new ActivityParticipantsRequestDTO(
      activity.getActivityVoluntaries() != null ? activity.getActivityVoluntaries().stream().filter(ActivityVoluntary::isRegistered).count() : 0L,
      activity.getVoluntariesRequest() != null ? activity.getVoluntariesRequest() : 0L
    );
  }
}
