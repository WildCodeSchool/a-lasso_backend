package com.back_alasso.AssociationFollower;

import java.util.UUID;

public record AssociationFollowerDTO(boolean isFollow, boolean isNotificationActive, UUID associationId) {
  public static AssociationFollowerDTO fromEntityToDTO(AssociationFollower associationFollower) {
    return new AssociationFollowerDTO(
      associationFollower.isIs_follow(),
      associationFollower.isIs_notification_active(),
      associationFollower.getAssociation().getId()
    );
  }
}
