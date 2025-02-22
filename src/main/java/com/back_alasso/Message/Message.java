package com.back_alasso.Message;

import com.back_alasso.Activity.Activity;
import com.back_alasso.User.User;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Message extends BaseEntity {

  public static final int CONTENT_MAX_LENGTH = 250;

  @Column(nullable = false, length = CONTENT_MAX_LENGTH)
  private String content;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "activity_id")
  private Activity activity;

  public Message(String content, User user, Activity activity) {
    this.content = content;
    this.user = user;
    this.activity = activity;
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
}
