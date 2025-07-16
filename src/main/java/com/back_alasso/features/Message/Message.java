package com.back_alasso.features.Message;

import com.back_alasso.core.BaseEntity;
import com.back_alasso.features.Activity.Activity;
import com.back_alasso.features.User.User;
import com.back_alasso.features.UserMessage.UserMessage;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message extends BaseEntity {

  public static final int CONTENT_MAX_LENGTH = 250;

  @Column(nullable = false)
  private LocalDateTime date;

  @Column(nullable = false, length = CONTENT_MAX_LENGTH)
  private String content;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "activity_id")
  private Activity activity;

  @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserMessage> userMessages;

  public Message(String content, User user, Activity activity, LocalDateTime date) {
    this.content = content;
    this.user = user;
    this.activity = activity;
    this.date = date;
  }
}
