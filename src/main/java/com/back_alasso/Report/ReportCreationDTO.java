package com.back_alasso.Report;

public record ReportCreationDTO(ReportUser reportedUser, String messageReporter, ReasonReportEnumType reportType) {}
