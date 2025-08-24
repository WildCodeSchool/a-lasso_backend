package com.back_alasso.unit.Report;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.back_alasso.features.Activity.Activity;
import com.back_alasso.features.Activity.ActivityRepository;
import com.back_alasso.features.ActivityVoluntary.ActivityVoluntary;
import com.back_alasso.features.ActivityVoluntary.ActivityVoluntaryRepository;
import com.back_alasso.features.Association.Association;
import com.back_alasso.features.Association.AssociationRepository;
import com.back_alasso.features.AssociationFollower.AssociationFollower;
import com.back_alasso.features.AssociationFollower.AssociationFollowerRepository;
import com.back_alasso.features.Report.Report;
import com.back_alasso.features.Report.ReportRepository;
import com.back_alasso.features.Report.ReportService;
import com.back_alasso.features.Report.StatusReportEnumType;
import com.back_alasso.features.User.AccountEnumType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ReportServiceTest {

    @Mock
    private AssociationRepository associationRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ActivityVoluntaryRepository activityVoluntaryRepository;

    @Mock
    private AssociationFollowerRepository associationFollowerRepository;

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    private UUID associationId;
    private Association association;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        associationId = UUID.randomUUID();
        association = new Association();
        association.setId(associationId);
        association.setAccount_status(AccountEnumType.ACTIVE);
    }

    @Test
    void banAssociation_shouldBanAssociationAndCleanupRelations() {
        Activity activity = new Activity();
        activity.setId(UUID.randomUUID());

        ActivityVoluntary activityVoluntary = new ActivityVoluntary();
        activityVoluntary.setId(UUID.randomUUID());

        AssociationFollower follower = new AssociationFollower();
        follower.setId(UUID.randomUUID());

        Report report = new Report();
        report.setId(UUID.randomUUID());
        report.setStatus(StatusReportEnumType.IN_PROGRESS);

        when(associationRepository.findById(associationId)).thenReturn(Optional.of(association));
        when(activityRepository.findAllByAssociation_id(associationId)).thenReturn(List.of(activity));
        when(activityVoluntaryRepository.findAllByActivity_id(activity.getId())).thenReturn(List.of(activityVoluntary));
        when(associationFollowerRepository.findAllByAssociation_id(associationId)).thenReturn(List.of(follower));
        when(reportRepository.findAllByUserReportedId(associationId)).thenReturn(List.of(report));

        boolean result = reportService.banAssociation(associationId);
        assertThat(result).isTrue();

        verify(activityVoluntaryRepository).deleteAll(List.of(activityVoluntary));
        verify(associationFollowerRepository).deleteAll(List.of(follower));

        assertThat(report.getStatus()).isEqualTo(StatusReportEnumType.ASSOCIATION_BANNED);
        verify(reportRepository).saveAll(List.of(report));

        assertThat(association.getAccount_status()).isEqualTo(AccountEnumType.BANNED);
        verify(associationRepository).save(association);
    }
}