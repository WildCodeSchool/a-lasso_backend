package com.back_alasso.Message;

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

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  @GetMapping("/{activityId}")
  public ResponseEntity<List<MessageDTO>> getAllActivityMessages(@PathVariable UUID activityId) {
    List<MessageDTO> messages = messageService.getAllActivityMessages(activityId);
    return ResponseEntity.status(HttpStatus.OK).body(messages);
  }
}
