package com.back_alasso.Report;

import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.User.User;
import com.back_alasso.User.UserService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

  private final ReportRepository reportRepository;
  private final UserService userService;

  public ReportService(ReportRepository reportRepository, UserService userService) {
    this.reportRepository = reportRepository;
    this.userService = userService;
  }

  public List<ReportDTO> getAllReports() {
    List<Report> reports = reportRepository.findAll();

    if (reports.isEmpty()) {
      throw new ResourceNotFoundException("reports not found");
    }

    return reports.stream().map(report -> ReportDTO.fromEntityToDTO(report)).collect(Collectors.toList());
  }

  public Boolean createReport(ReportCreationDTO newReport, UUID authenticatedUserId) {
    User reportedUser = userService.findById(newReport.reportedUser().id());
    User reporterUser = userService.findById(authenticatedUserId);

    Report report = new Report(
      StatusReportEnumType.IN_PROGRESS,
      newReport.reportType(),
      reportedUser,
      reporterUser,
      null,
      newReport.messageReporter(),
      null
    );

    reportRepository.save(report);
    return true;
  }
}
