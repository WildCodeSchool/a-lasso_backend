package com.back_alasso.features.Message;

import com.back_alasso.features.Authentication.AuthService;
import com.back_alasso.features.Message.DTO.MessageCreationRequestDTO;
import com.back_alasso.features.Message.DTO.MessageResponseDTO;
import com.back_alasso.features.User.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

  public final MessageService messageService;
  public final AuthService authService;
  public final UserService userService;

  public MessageController(MessageService messageService, AuthService authService, UserService userService) {
    this.messageService = messageService;
    this.authService = authService;
    this.userService = userService;
  }

  @GetMapping("/{activityId}")
  public ResponseEntity<List<MessageResponseDTO>> getAllActivityMessages(
    @PathVariable UUID activityId,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);

    List<MessageResponseDTO> messages = messageService.getAllActivityMessages(activityId, authenticatedUserId);
    return ResponseEntity.status(HttpStatus.OK).body(messages);
  }

  @PostMapping
  public ResponseEntity<MessageResponseDTO> createMessage(
    @Valid @RequestBody MessageCreationRequestDTO newMessage,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);

    MessageResponseDTO savedMessage = messageService.createMessage(newMessage, authenticatedUserId);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
  }
}
