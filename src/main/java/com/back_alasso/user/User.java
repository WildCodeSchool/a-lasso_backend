package com.back_alasso.user;

import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;

@Entity
public class User extends BaseEntity {

  public static final int EMAIL_MAX_LENGTH = 320;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserEnumType user_type;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AccountEnumType account_status;

  @Column(nullable = false)
  private String hashed_password;

  @Column(nullable = false, length = EMAIL_MAX_LENGTH)
  private String email;
}
