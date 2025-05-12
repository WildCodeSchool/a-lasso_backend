package com.back_alasso.Report;

import com.back_alasso.User.User;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Report extends BaseEntity {

  public static final int MESSAGE_REPORTER_MAX_LENGTH = 700;
  public static final int COMMENTARY_ADMIN_MAX_LENGTH = 1000;

  @Column(length = MESSAGE_REPORTER_MAX_LENGTH)
  private String messageReporter;

  @Column(length = COMMENTARY_ADMIN_MAX_LENGTH)
  private String commentaryAdmin;

  @Column(nullable = false)
  private StatusReportEnumType status;

  @Column(nullable = false)
  private ReasonReportEnumType reason;

  @ManyToOne
  @JoinColumn(name = "userReportedId")
  private User userReported;

  @ManyToOne
  @JoinColumn(name = "userReporterId")
  private User userReporter;

  @ManyToOne
  @JoinColumn(name = "userAdminId")
  private User userAdmin;

  // Necessary to have an empty constructor to instance object.
  public Report() {}

  public Report(
    StatusReportEnumType status,
    ReasonReportEnumType reason,
    User userReported,
    User userReporter,
    User userAdmin,
    String messageReporter,
    String commentaryAdmin
  ) {
    this.status = status;
    this.reason = reason;
    this.userReported = userReported;
    this.userReporter = userReporter;
    this.userAdmin = userAdmin;
    this.messageReporter = messageReporter;
    this.commentaryAdmin = commentaryAdmin;
  }

  public String getMessageReporter() {
    return messageReporter;
  }

  public void setMessageReporter(String messageReporter) {
    this.messageReporter = messageReporter;
  }

  public String getCommentaryAdmin() {
    return commentaryAdmin;
  }

  public void setCommentaryAdmin(String commentaryAdmin) {
    this.commentaryAdmin = commentaryAdmin;
  }

  public StatusReportEnumType getStatus() {
    return status;
  }

  public void setStatus(StatusReportEnumType status) {
    this.status = status;
  }

  public ReasonReportEnumType getReason() {
    return reason;
  }

  public void setReason(ReasonReportEnumType reason) {
    this.reason = reason;
  }

  public User getUserReported() {
    return userReported;
  }

  public void setUserReported(User userReported) {
    this.userReported = userReported;
  }

  public User getUserReporter() {
    return userReporter;
  }

  public void setUserReporter(User userReporter) {
    this.userReporter = userReporter;
  }

  public User getUserAdmin() {
    return userAdmin;
  }

  public void setUserAdmin(User userAdmin) {
    this.userAdmin = userAdmin;
  }
}
