package com.back_alasso.Message;

import com.back_alasso.Authentication.AuthService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend
public class MessageController {

  public final MessageService messageService;
  public final AuthService authService;

  public MessageController(MessageService messageService, AuthService authService) {
    this.messageService = messageService;
    this.authService = authService;
  }

  @GetMapping("/{activityId}")
  public ResponseEntity<List<MessageDTO>> getAllActivityMessages(@PathVariable UUID activityId, @RequestHeader("Authorization") String token) {
    UUID authenticatedUser = authService.getUserIdFromToken(token);
    List<MessageDTO> messages = messageService.getAllActivityMessages(activityId, authenticatedUser);
    return ResponseEntity.status(HttpStatus.OK).body(messages);
  }

  @PostMapping
  public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageCreationDTO newMessage, @RequestHeader("Authorization") String token) {
    UUID authenticatedUser = authService.getUserIdFromToken(token);
    MessageDTO savedMessage = messageService.createMessage(newMessage, authenticatedUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
  }
}
