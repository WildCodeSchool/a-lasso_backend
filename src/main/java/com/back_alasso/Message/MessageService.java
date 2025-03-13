package com.back_alasso.Message;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  private final MessageRepository messageRepository;

  public MessageService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public List<MessageDTO> getAllActivityMessages(UUID activityId) {
    List<Message> messages = messageRepository.findAllByActivity_Id(activityId);

    return messages.stream().map(MessageDTO::fromEntityToDTO).collect(Collectors.toList());
  }
}
