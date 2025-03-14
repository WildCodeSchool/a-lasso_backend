package com.back_alasso.User;

import com.back_alasso.Geolocalisation.Geolocalisation;
import com.back_alasso.Message.Message;
import com.back_alasso.Preferences.Preferences;
import com.back_alasso.Report.Report;
import com.back_alasso.Statistic.Statistic;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Inheritance(strategy = InheritanceType.JOINED) // Use separate tables for each entity
@Entity
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

  @OneToMany(mappedBy = "user")
  private List<Message> messages;

  @OneToMany(mappedBy = "user_reporter")
  private List<Report> reportsMade; // reports where the user is the reporter

  @OneToMany(mappedBy = "user_reported")
  private List<Report> reportsReceived; // reports where the user is the reported

  @OneToMany(mappedBy = "user_admin")
  private List<Report> reportsHandled; // reports where the user is the admin

  @ManyToOne
  @JoinColumn(name = "geolocalisation_id")
  private Geolocalisation geolocalisation;

  @OneToMany(mappedBy = "user")
  private List<Statistic> statistic;

  // Necessary to have an empty constructor to instance object.
  public User() {}

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
    // return true - TODO complete
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    // return true - TODO complete
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // return true - TODO complete
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    // return true - TODO complete
    return UserDetails.super.isEnabled();
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

  public Preferences getPreferences() {
    return preferences;
  }

  public void setPreferences(Preferences preferences) {
    this.preferences = preferences;
  }

  public List<Message> getMessage() {
    return messages;
  }

  public void setMessage(List<Message> messages) {
    this.messages = messages;
  }

  public List<Message> getMessages() {
    return messages;
  }

  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }

  public List<Report> getReportsMade() {
    return reportsMade;
  }

  public void setReportsMade(List<Report> reportsMade) {
    this.reportsMade = reportsMade;
  }

  public List<Report> getReportsReceived() {
    return reportsReceived;
  }

  public void setReportsReceived(List<Report> reportsReceived) {
    this.reportsReceived = reportsReceived;
  }

  public List<Report> getReportsHandled() {
    return reportsHandled;
  }

  public void setReportsHandled(List<Report> reportsHandled) {
    this.reportsHandled = reportsHandled;
  }

  public Geolocalisation getGeolocalisation() {
    return geolocalisation;
  }

  public void setGeolocalisation(Geolocalisation geolocalisation) {
    this.geolocalisation = geolocalisation;
  }

  public List<Statistic> getStatistic() {
    return statistic;
  }

  public void setStatistic(List<Statistic> statistic) {
    this.statistic = statistic;
  }

  public Set<UserEnumType> getRoles() {
    return roles;
  }

  public void setRoles(Set<UserEnumType> roles) {
    this.roles = roles;
  }
}
