package com.back_alasso.Report;

import java.time.LocalDateTime;

public record ReportDTO(
  ReportUser reportedUser,
  ReportUser reporterUser,
  String messageReporter,
  ReasonReportEnumType reportType,
  StatusReportEnumType status,
  String commentaryAdmin,
  LocalDateTime createdAt
  //        String isReportBy
) {
  public static ReportDTO fromEntityToDTO(Report report) {
    return new ReportDTO(
      new ReportUser(report.getUser_reported().getId(), report.getUser_reported().getUsername()),
      new ReportUser(report.getUser_reporter().getId(), report.getUser_reporter().getUsername()),
      report.getMessage_reporter(),
      report.getReason(),
      report.getStatus(),
      report.getCommentary_admin(),
      report.getCreatedAt()
      //                report.getUser_reporter().getRoles()
    );
  }
}
