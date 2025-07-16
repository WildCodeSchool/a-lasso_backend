package com.back_alasso.features.Message;

import com.back_alasso.exception.ResourceNotFoundException;
import com.back_alasso.features.Activity.Activity;
import com.back_alasso.features.Activity.ActivityRepository;
import com.back_alasso.features.Message.DTO.MessageCreationRequestDTO;
import com.back_alasso.features.Message.DTO.MessageResponseDTO;
import com.back_alasso.features.User.User;
import com.back_alasso.features.User.UserRepository;
import com.back_alasso.features.UserMessage.UserMessageService;
import com.back_alasso.features.Voluntary.Voluntary;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final ActivityRepository activityRepository;
  private final UserMessageService userMessageService;

  public MessageService(
    MessageRepository messageRepository,
    UserRepository userRepository,
    ActivityRepository activityRepository,
    UserMessageService userMessageService
  ) {
    this.messageRepository = messageRepository;
    this.userRepository = userRepository;
    this.activityRepository = activityRepository;
    this.userMessageService = userMessageService;
  }

  public List<MessageResponseDTO> getAllActivityMessages(UUID activityId, UUID authenticatedUser) {
    List<Message> messages = messageRepository.findAllByActivity_Id(activityId);

    messages.forEach(message -> {
      userMessageService.markAsRead(authenticatedUser, message.getId());
    });

    return messages.stream().map(message -> MessageResponseDTO.fromEntityToDTO(message, authenticatedUser)).collect(Collectors.toList());
  }

  public MessageResponseDTO createMessage(MessageCreationRequestDTO newMessage, UUID authenticatedUser) {
    User user = userRepository.findById(authenticatedUser).orElseThrow(() -> new ResourceNotFoundException("L'utilisateur n'a pas été trouvé."));
    Activity activity = activityRepository
      .findById(newMessage.activityId())
      .orElseThrow(() -> new ResourceNotFoundException("L'activité n'a pas été trouvée."));

    Message createdMessage = messageRepository.save(new Message(newMessage.content(), user, activity, newMessage.date()));

    List<User> allUsers = new ArrayList<>();
    List<Voluntary> volunteers = activityRepository.findVolunteersByActivityId(newMessage.activityId());
    allUsers.addAll(volunteers);
    allUsers.add(activity.getAssociation());

    userMessageService.createForAllUsers(createdMessage, allUsers, authenticatedUser);

    return MessageResponseDTO.fromEntityToDTO(createdMessage, authenticatedUser);
  }
}
