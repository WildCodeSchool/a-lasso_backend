package com.back_alasso.Report;

import com.back_alasso.Activity.Activity;
import com.back_alasso.Activity.ActivityRepository;
import com.back_alasso.ActivityVoluntary.ActivityVoluntary;
import com.back_alasso.ActivityVoluntary.ActivityVoluntaryRepository;
import com.back_alasso.Association.Association;
import com.back_alasso.Association.AssociationRepository;
import com.back_alasso.AssociationFollower.AssociationFollower;
import com.back_alasso.AssociationFollower.AssociationFollowerRepository;
import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.User.AccountEnumType;
import com.back_alasso.User.User;
import com.back_alasso.User.UserService;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.Voluntary.VoluntaryRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

  private final ReportRepository reportRepository;
  private final UserService userService;
  private final VoluntaryRepository voluntaryRepository;
  private final AssociationRepository associationRepository;
  private final ActivityVoluntaryRepository activityVoluntaryRepository;
  private final AssociationFollowerRepository associationFollowerRepository;
  private final ActivityRepository activityRepository;

  public ReportService(
    ReportRepository reportRepository,
    UserService userService,
    AssociationRepository associationRepository,
    VoluntaryRepository voluntaryRepository,
    ActivityVoluntaryRepository activityVoluntaryRepository,
    AssociationFollowerRepository associationFollowerRepository,
    ActivityRepository activityRepository
  ) {
    this.reportRepository = reportRepository;
    this.userService = userService;
    this.associationRepository = associationRepository;
    this.voluntaryRepository = voluntaryRepository;
    this.activityVoluntaryRepository = activityVoluntaryRepository;
    this.associationFollowerRepository = associationFollowerRepository;
    this.activityRepository = activityRepository;
  }

  public List<ReportDTO> getAllReportsInProgress() {
    List<Report> reports = reportRepository.findByStatus(StatusReportEnumType.IN_PROGRESS);

    return reports.stream().map(report -> ReportDTO.fromEntityToDTO(report)).collect(Collectors.toList());
  }

  public List<ReportDTO> getAllReportsOfReportedId(UUID reportedId) {
    List<Report> reports = reportRepository.findAllByUserReportedId(reportedId);

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

  public Boolean updateReport(ReportDTO updateReport) {
    Report reportToUpdate = reportRepository
      .findById(updateReport.reportId())
      .orElseThrow(() -> new ResourceNotFoundException("Report to update not found"));

    reportToUpdate.setCommentaryAdmin(updateReport.commentaryAdmin());
    reportToUpdate.setStatus(updateReport.status());

    reportRepository.save(reportToUpdate);
    return true;
  }

  public Boolean banAssociation(UUID userId) {
    Association associationToBan = associationRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Association not found"));

    List<Activity> activities = activityRepository.findAllByAssociation_id(associationToBan.getId());

    if (!activities.isEmpty()) {
      activities.forEach(activity -> {
        List<ActivityVoluntary> activityVoluntaries = activityVoluntaryRepository.findAllByActivity_id(activity.getId());
        if (!activityVoluntaries.isEmpty()) {
          activityVoluntaryRepository.deleteAll(activityVoluntaries);
        }
      });
    }

    List<AssociationFollower> associationFollowers = associationFollowerRepository.findAllByAssociation_id(associationToBan.getId());

    if (!associationFollowers.isEmpty()) {
      associationFollowerRepository.deleteAll(associationFollowers);
    }

    List<Report> reports = reportRepository.findAllByUserReportedId(associationToBan.getId());

    if (!reports.isEmpty()) {
      reports.forEach(report -> {
        report.setStatus(StatusReportEnumType.ASSOCIATION_BANNED);
      });
      reportRepository.saveAll(reports);
    }

    associationToBan.setAccount_status(AccountEnumType.BANNED);
    associationRepository.save(associationToBan);
    return true;
  }

  public boolean banVoluntary(UUID userId) {
    Voluntary voluntaryToBan = voluntaryRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Voluntary not found"));

    List<ActivityVoluntary> activitiesVoluntary = activityVoluntaryRepository.findAllByVoluntary_idAndRegistered(voluntaryToBan.getId(), true);

    if (!activitiesVoluntary.isEmpty()) {
      activitiesVoluntary.forEach(activityVoluntary -> activityVoluntary.setRegistered(false));
      activityVoluntaryRepository.saveAll(activitiesVoluntary);
    }

    List<Report> reports = reportRepository.findAllByUserReportedId(voluntaryToBan.getId());

    if (!reports.isEmpty()) {
      reports.forEach(report -> {
        report.setStatus(StatusReportEnumType.VOLUNTARY_BANNED);
      });
      reportRepository.saveAll(reports);
    }

    voluntaryToBan.setAccount_status(AccountEnumType.BANNED);
    voluntaryRepository.save(voluntaryToBan);
    return true;
  }
}
