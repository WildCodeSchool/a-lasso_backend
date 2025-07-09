package com.back_alasso.Report;

import com.back_alasso.User.UserService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportController {

  public final ReportService reportService;
  public final UserService userService;

  public ReportController(ReportService reportService, UserService userService) {
    this.reportService = reportService;
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<ReportDTO>> getAllReportsInProgress() {
    List<ReportDTO> reports = reportService.getAllReportsInProgress();

    return ResponseEntity.status(HttpStatus.OK).body(reports);
  }

  @GetMapping("/{reportedId}")
  public ResponseEntity<List<ReportDTO>> getAllReportsOfReportedId(@PathVariable UUID reportedId) {
    List<ReportDTO> reports = reportService.getAllReportsOfReportedId(reportedId);

    return ResponseEntity.status(HttpStatus.OK).body(reports);
  }

  @PostMapping
  public ResponseEntity<Boolean> createReport(@RequestBody ReportCreationDTO reportCreation, @AuthenticationPrincipal UserDetails userDetails) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);

    Boolean isSuccessPostReport = reportService.createReport(reportCreation, authenticatedUserId);

    return ResponseEntity.status(HttpStatus.CREATED).body(isSuccessPostReport);
  }

  @PutMapping
  public ResponseEntity<Boolean> updateReport(@RequestBody ReportDTO updateReport) {
    Boolean hasUpdatedReportSuccessfully = reportService.updateReport(updateReport);

    return ResponseEntity.status(HttpStatus.OK).body(hasUpdatedReportSuccessfully);
  }
}
