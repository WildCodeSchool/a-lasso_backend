package com.back_alasso.Statistic;

public record StatisticDTO(Integer value, String description) {
  public static StatisticDTO fromEntityToDTO(Statistic statistics) {
    return new StatisticDTO(statistics.getValue(), statistics.getDescription());
  }
}
