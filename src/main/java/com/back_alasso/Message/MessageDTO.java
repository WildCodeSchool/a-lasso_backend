package com.back_alasso.Message;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageDTO(UUID id, LocalDateTime date, String content, boolean isSendByUserConnected, UUID activityId) {
  public static MessageDTO fromEntityToDTO(Message message) {
    // TODO: récupérer le user via le header de la requête
    return new MessageDTO(
      message.getId(),
      message.getDate(),
      message.getContent(),
      true, // TODO: à changer
      message.getActivity().getId()
    );
  }
}
