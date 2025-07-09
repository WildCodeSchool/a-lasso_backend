package com.back_alasso.Message;

import com.back_alasso.Activity.Activity;
import com.back_alasso.Activity.ActivityRepository;
import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.User.User;
import com.back_alasso.User.UserRepository;
import com.back_alasso.UserMessage.UserMessageService;
import com.back_alasso.Voluntary.Voluntary;
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

  public List<MessageDTO> getAllActivityMessages(UUID activityId, UUID authenticatedUser) {
    List<Message> messages = messageRepository.findAllByActivity_Id(activityId);

    messages.forEach(message -> {
      userMessageService.markAsRead(authenticatedUser, message.getId());
    });

    return messages.stream().map(message -> MessageDTO.fromEntityToDTO(message, authenticatedUser)).collect(Collectors.toList());
  }

  public MessageDTO createMessage(MessageCreationDTO newMessage, UUID authenticatedUser) {
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

    return MessageDTO.fromEntityToDTO(createdMessage, authenticatedUser);
  }
}
