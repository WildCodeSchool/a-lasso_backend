package com.back_alasso.User;

import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED) // Use separate tables for each entity
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

  public User(UserEnumType user_type, AccountEnumType account_status, String hashed_password, String email) {
    this.user_type = user_type;
    this.account_status = account_status;
    this.hashed_password = hashed_password;
    this.email = email;
  }

  public UserEnumType getUser_type() {
    return user_type;
  }

  public void setUser_type(UserEnumType user_type) {
    this.user_type = user_type;
  }

  public AccountEnumType getAccount_status() {
    return account_status;
  }

  public void setAccount_status(AccountEnumType account_status) {
    this.account_status = account_status;
  }

  public String getHashed_password() {
    return hashed_password;
  }

  public void setHashed_password(String hashed_password) {
    this.hashed_password = hashed_password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
