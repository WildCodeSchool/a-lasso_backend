package com.back_alasso.Statistic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StatisticDTO(
  @NotNull(message = "La valeur de la statistic ne peux pas être vide") Integer value,
  @NotBlank(message = "La description de la statistic ne peux pas être vide") String description
) {
  public static StatisticDTO fromEntityToDTO(Statistic statistics) {
    return new StatisticDTO(statistics.getValue(), statistics.getDescription());
  }
}
