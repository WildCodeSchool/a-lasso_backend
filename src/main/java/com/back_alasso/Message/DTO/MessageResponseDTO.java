package com.back_alasso.Message.DTO;

import com.back_alasso.Association.Association;
import com.back_alasso.Message.Message;
import com.back_alasso.User.User;
import com.back_alasso.Voluntary.Voluntary;
import java.time.LocalDateTime;
import java.util.UUID;

public record MessageResponseDTO(UUID id, LocalDateTime date, String content, boolean isSendByUserConnected, UUID activityId, String author) {
  public static MessageResponseDTO fromEntityToDTO(Message message, UUID authenticatedUser) {
    User user = message.getUser();
    String authorMessage = (user instanceof Association) ? ((Association) user).getName() : ((Voluntary) user).getFirst_name();

    boolean isSendByUser = user.getId() == authenticatedUser;

    return new MessageResponseDTO(
      message.getId(),
      message.getDate(),
      message.getContent(),
      isSendByUser,
      message.getActivity().getId(),
      isSendByUser ? "Moi" : authorMessage
    );
  }
}
