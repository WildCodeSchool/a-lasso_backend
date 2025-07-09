package com.back_alasso.User;

import com.back_alasso.Geolocation.Geolocation;
import com.back_alasso.Message.Message;
import com.back_alasso.Preferences.Preferences;
import com.back_alasso.Report.Report;
import com.back_alasso.Statistic.Statistic;
import com.back_alasso.UserMessage.UserMessage;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

  public static final int EMAIL_MAX_LENGTH = 320;

  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<UserEnumType> roles = new HashSet<>();

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AccountEnumType account_status;

  @Column(nullable = false)
  private String hashed_password;

  @Column(nullable = false, length = EMAIL_MAX_LENGTH, unique = true)
  private String email;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Preferences preferences;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Message> messages;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserMessage> userMessages;

  @OneToMany(mappedBy = "userReporter", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Report> reportsMade;

  @OneToMany(mappedBy = "userReported", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Report> reportsReceived;

  @OneToMany(mappedBy = "userAdmin", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Report> reportsHandled;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Statistic> statistic;

  @ManyToOne
  @JoinColumn(name = "geolocation_id")
  private Geolocation geolocation;

  // Custom constructor without collections (optional)
  public User(Set<UserEnumType> roles, AccountEnumType account_status, String hashed_password, String email, Preferences preferences) {
    this.roles = roles;
    this.account_status = account_status;
    this.hashed_password = hashed_password;
    this.email = email;
    this.preferences = preferences;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream().map(Enum::name).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
  }

  @Override
  public String getPassword() {
    return hashed_password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
