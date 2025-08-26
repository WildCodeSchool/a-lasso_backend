package com.back_alasso.features.AssociationFollower;

import java.util.UUID;

public record AssociationFollowerDTO(boolean isFollow, boolean isNotificationActive, UUID associationId) {
  public static AssociationFollowerDTO fromEntityToDTO(AssociationFollower associationFollower) {
    return new AssociationFollowerDTO(
      associationFollower.isFollow(),
      associationFollower.isNotification_active(),
      associationFollower.getAssociation().getId()
    );
  }
}
