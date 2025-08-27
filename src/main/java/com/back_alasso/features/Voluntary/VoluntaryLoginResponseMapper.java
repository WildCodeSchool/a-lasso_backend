package com.back_alasso.features.Voluntary;

import com.back_alasso.features.ActivityVoluntary.DTO.ActivityVoluntaryLoginResponseDTO;
import com.back_alasso.features.AssociationFollower.AssociationFollower;
import com.back_alasso.features.AssociationFollower.AssociationFollowerDTO;
import com.back_alasso.features.Geolocation.DTO.GeolocationLoginDTO;
import com.back_alasso.features.Image.DTO.ImageResponseDTO;
import com.back_alasso.features.Image.ImageEnumType;
import com.back_alasso.features.Image.ImageMapper;
import com.back_alasso.features.Report.ReportRepository;
import com.back_alasso.features.Report.StatusReportEnumType;
import com.back_alasso.features.User.UserEnumType;
import com.back_alasso.features.UserMessage.UserMessageNotificationDTO;
import com.back_alasso.features.Voluntary.DTO.VoluntaryLoginResponseDTO;
import com.back_alasso.shared.NotificationDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoluntaryLoginResponseMapper {

  private final ImageMapper imageMapper;
  private final ReportRepository reportRepository;

  public VoluntaryLoginResponseDTO fromEntityToDTO(Voluntary voluntary) {
    ImageResponseDTO avatar = voluntary.getAvatar() != null
      ? imageMapper.toResponseDTOs(List.of(voluntary.getAvatar().getId()), ImageEnumType.AVATAR).stream().findFirst().orElse(null)
      : null;

    List<AssociationFollowerDTO> followed = voluntary
      .getAssociationFollowers()
      .stream()
      .filter(AssociationFollower::isFollow)
      .map(AssociationFollowerDTO::fromEntityToDTO)
      .toList();

    List<ActivityVoluntaryLoginResponseDTO> activitiesUserInfos = voluntary
      .getActivityVoluntaries()
      .stream()
      .map(ActivityVoluntaryLoginResponseDTO::fromEntityToDTO)
      .toList();

    boolean isAdmin = voluntary.getRoles().contains(UserEnumType.ROLE_ADMIN);
    Integer reportsInProgress = isAdmin ? reportRepository.countByStatus(StatusReportEnumType.IN_PROGRESS) : null;

    List<UserMessageNotificationDTO> messageNotifications = voluntary
      .getUserMessages()
      .stream()
      .filter(userMessage -> !userMessage.isRead())
      .collect(Collectors.groupingBy(userMessage -> userMessage.getMessage().getActivity(), Collectors.counting()))
      .entrySet()
      .stream()
      .map(entry -> new UserMessageNotificationDTO(entry.getKey().getId(), entry.getKey().getTitle(), entry.getValue().intValue()))
      .toList();

    NotificationDTO notification = new NotificationDTO(messageNotifications, reportsInProgress);

    return new VoluntaryLoginResponseDTO(
      "voluntary",
      voluntary.getEmail(),
      voluntary.getFirst_name(),
      voluntary.getLast_name(),
      voluntary.getMobile_phone(),
      voluntary.getCity(),
      voluntary.getCountry(),
      avatar,
      followed,
      activitiesUserInfos,
      notification,
      GeolocationLoginDTO.from(voluntary.getGeolocation()),
      voluntary.getCreatedAt().toLocalDate(),
      voluntary.getBirth_date().atStartOfDay().toLocalDate()
    );
  }
}
