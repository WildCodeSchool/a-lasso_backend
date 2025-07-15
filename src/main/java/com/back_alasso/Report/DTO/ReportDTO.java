package com.back_alasso.Report.DTO;

import com.back_alasso.Association.Association;
import com.back_alasso.Report.ReasonReportEnumType;
import com.back_alasso.Report.Report;
import com.back_alasso.Report.StatusReportEnumType;
import com.back_alasso.User.User;
import com.back_alasso.User.UserEnumType;
import com.back_alasso.Voluntary.Voluntary;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record ReportDTO(
  @NotNull(message = "L'id ne peut pas être null") UUID reportId,
  @Valid @NotNull(message = "Le reported user ne peut pas être null") ReportUserDTO reportedUser,
  @Valid @NotNull(message = "Le reporter user ne peut pas être null") ReportUserDTO reporterUser,
  @NotBlank(message = "Le message du reporter ne peut pas être vide") String messageReporter,
  @NotNull(message = "Le type ne peut pas être null") ReasonReportEnumType reportType,
  @NotNull(message = "Le status ne peut pas être null") StatusReportEnumType status,
  String commentaryAdmin,
  @NotNull(message = "la date de création du report doit être renseigné") LocalDateTime createdAt
) {
  public static ReportDTO fromEntityToDTO(Report report) {
    User reported = report.getUserReported();
    User reporter = report.getUserReporter();

    return new ReportDTO(
      report.getId(),
      new ReportUserDTO(report.getUserReported().getId(), getDisplayName(reported), getUserType(reported)),
      new ReportUserDTO(report.getUserReporter().getId(), getDisplayName(reporter), getUserType(reporter)),
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
