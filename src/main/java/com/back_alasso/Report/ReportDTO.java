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
) {
  public static ReportDTO fromEntityToDTO(Report report) {
    User reported = report.getUserReported();
    User reporter = report.getUserReporter();

    return new ReportDTO(
      report.getId(),
      new ReportUser(report.getUserReported().getId(), getDisplayName(reported), getUserType(reported)),
      new ReportUser(report.getUserReporter().getId(), getDisplayName(reporter), getUserType(reporter)),
      report.getMessageReporter(),
      report.getReason(),
      report.getStatus(),
      report.getCommentaryAdmin(),
      report.getCreatedAt()
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
