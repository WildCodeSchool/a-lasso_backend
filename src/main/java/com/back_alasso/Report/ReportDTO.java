package com.back_alasso.Report;

import com.back_alasso.Association.Association;
import com.back_alasso.User.User;
import com.back_alasso.User.UserEnumType;
import com.back_alasso.Voluntary.Voluntary;
import java.time.LocalDateTime;
import java.util.UUID;

public record ReportDTO(
  UUID reportId,
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
    User reported = report.getUser_reported();
    User reporter = report.getUser_reporter();

    return new ReportDTO(
      report.getId(),
      new ReportUser(report.getUser_reported().getId(), getDisplayName(reported), getUserType(reported)),
      new ReportUser(report.getUser_reporter().getId(), getDisplayName(reporter), getUserType(reporter)),
      report.getMessage_reporter(),
      report.getReason(),
      report.getStatus(),
      report.getCommentary_admin(),
      report.getCreatedAt()
      //                report.getUser_reporter().getRoles()
    );
  }

  private static String getUserType(User user) {
    return user.getRoles().contains(UserEnumType.ROLE_ASSOCIATION) ? "association" : "voluntary";
  }

  private static String getDisplayName(User user) {
    if (user instanceof Association) {
      return ((Association) user).getName();
    } else if (user instanceof Voluntary) {
      Voluntary voluntary = (Voluntary) user;
      return voluntary.getFirst_name() + " " + voluntary.getLast_name();
    } else {
      return user.getUsername();
    }
  }
}
