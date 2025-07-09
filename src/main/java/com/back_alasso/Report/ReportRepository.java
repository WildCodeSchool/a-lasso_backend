package com.back_alasso.Report;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, UUID> {
  int countByStatus(StatusReportEnumType status);

  List<Report> findByStatus(StatusReportEnumType status);

  List<Report> findByUserReportedId(UUID reportedId);
}
