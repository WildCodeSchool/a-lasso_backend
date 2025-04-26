package com.back_alasso.UserMessage;

import com.back_alasso.Message.Message;
import com.back_alasso.User.User;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserMessage extends BaseEntity {

  @Column(name = "is_read", nullable = false)
  private boolean isRead = false;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "message_id", nullable = false)
  private Message message;

  public UserMessage() {}

  public UserMessage(boolean isRead, User user, Message message) {
    this.isRead = isRead;
    this.user = user;
    this.message = message;
  }

  public boolean isRead() {
    return isRead;
  }

  public void setRead(boolean read) {
    this.isRead = read;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
  }
}
