package com.back_alasso.Statistic;

import com.back_alasso.User.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record StatisticDTO(Integer value,String description ) {

    public static StatisticDTO fromEntityToDTO(Statistic statistics) {
      return new StatisticDTO(
              statistics.getValue(),
              statistics.getDescription()
      );
    }
}

