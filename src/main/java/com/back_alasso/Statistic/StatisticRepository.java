package com.back_alasso.Statistic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatisticRepository extends JpaRepository<Statistic, UUID> {
}
