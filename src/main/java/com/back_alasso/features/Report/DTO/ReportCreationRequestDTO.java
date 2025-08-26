package com.back_alasso.features.Report.DTO;

import com.back_alasso.features.Report.ReasonReportEnumType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReportCreationRequestDTO(
  @Valid @NotNull(message = "Le report user ne peux pas être null") ReportUserDTO reportedUser,
  @NotBlank(message = "Le message du reporter ne doit pas être vide") String messageReporter,
  @NotNull(message = "Le report type doit être défini") ReasonReportEnumType reportType
) {}
