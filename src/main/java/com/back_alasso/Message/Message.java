package com.back_alasso.Message;

import com.back_alasso.Activity.Activity;
import com.back_alasso.User.User;
import com.back_alasso.UserMessage.UserMessage;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
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

  // Necessary to have an empty constructor to instance object.
  public Message() {}

  public Message(String content, User user, Activity activity, LocalDateTime date) {
    this.content = content;
    this.user = user;
    this.activity = activity;
    this.date = date;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Activity getActivity() {
    return activity;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public List<UserMessage> getUserMessages() {
    return userMessages;
  }

  public void setUserMessages(List<UserMessage> userMessages) {
    this.userMessages = userMessages;
  }
}
