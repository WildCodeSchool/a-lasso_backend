package com.back_alasso.Report;

import com.back_alasso.User.User;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
