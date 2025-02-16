package com.back_alasso.User;

import com.back_alasso.AssociationFollower.AssociationFollower;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;

import java.util.List;

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

  @OneToMany(mappedBy = "association")
  private List<AssociationFollower> associationFollowers;

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

  public List<AssociationFollower> getAssociationFollowers() {
    return associationFollowers;
  }

  public void setAssociationFollowers(List<AssociationFollower> associationFollowers) {
    this.associationFollowers = associationFollowers;
  }
}
