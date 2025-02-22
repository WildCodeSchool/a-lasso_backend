package com.back_alasso.Statistic;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<Statistic, UUID> {}
