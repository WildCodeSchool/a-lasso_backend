package com.back_alasso.UserMessage;

import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.Message.Message;
import com.back_alasso.User.User;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserMessageService {

  private final UserMessageRepository userMessageRepository;

  public UserMessageService(UserMessageRepository userMessageRepository) {
    this.userMessageRepository = userMessageRepository;
  }

  @Transactional
  public void createForAllUsers(Message message, List<User> users, UUID authorId) {
    List<UserMessage> userMessages = new ArrayList<>();

    for (User user : users) {
      boolean isAuthor = user.getId().equals(authorId);
      UserMessage newUserMessage = new UserMessage(isAuthor, user, message);
      userMessages.add(newUserMessage);
    }

    userMessageRepository.saveAll(userMessages);
  }

  @Transactional
  public void markAsRead(UUID userId, UUID messageId) {
    UserMessage userMessage = userMessageRepository
      .findByUserIdAndMessageId(userId, messageId)
      .orElseThrow(() -> new ResourceNotFoundException("UserMessage non trouvé"));

    if (!userMessage.isRead()) {
      userMessage.setRead(true);
      userMessageRepository.save(userMessage);
    }
  }
}
