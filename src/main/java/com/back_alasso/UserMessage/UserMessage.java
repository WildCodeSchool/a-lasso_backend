package com.back_alasso.UserMessage;

import com.back_alasso.Message.Message;
import com.back_alasso.User.User;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMessage extends BaseEntity {

  @Column(name = "is_read", nullable = false)
  private boolean isRead = false;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "message_id", nullable = false)
  private Message message;
}
