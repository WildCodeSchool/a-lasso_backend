package com.back_alasso.Report.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ReportUserDTO(
  @NotNull(message = "L'ID ne peux pas être null") UUID id,
  @NotBlank(message = "Le userName ne peux pas être vide") String userName,
  String type
) {}
