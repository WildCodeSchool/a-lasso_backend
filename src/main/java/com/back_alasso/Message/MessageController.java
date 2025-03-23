package com.back_alasso.Message;

import com.back_alasso.Authentication.AuthService;
import com.back_alasso.User.UserService;
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
  public ResponseEntity<List<MessageDTO>> getAllActivityMessages(@PathVariable UUID activityId, @AuthenticationPrincipal UserDetails userDetails) {
    String emailAuthenticatedUser = userDetails.getUsername();
    UUID authenticatedUser = userService.findByEmail(emailAuthenticatedUser).getId();

    List<MessageDTO> messages = messageService.getAllActivityMessages(activityId, authenticatedUser);
    return ResponseEntity.status(HttpStatus.OK).body(messages);
  }

  @PostMapping
  public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageCreationDTO newMessage, @AuthenticationPrincipal UserDetails userDetails) {
    String emailAuthenticatedUser = userDetails.getUsername();
    UUID authenticatedUser = userService.findByEmail(emailAuthenticatedUser).getId();

    MessageDTO savedMessage = messageService.createMessage(newMessage, authenticatedUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
  }
}
