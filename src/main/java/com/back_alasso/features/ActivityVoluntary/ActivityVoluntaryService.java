package com.back_alasso.features.ActivityVoluntary;

import com.back_alasso.core.MailService;
import com.back_alasso.exception.ResourceNotFoundException;
import com.back_alasso.features.Activity.Activity;
import com.back_alasso.features.Activity.ActivityRepository;
import com.back_alasso.features.Activity.ActivityService;
import com.back_alasso.features.ActivityVoluntary.DTO.ActivityParticipantsRequestDTO;
import com.back_alasso.features.Address.Address;
import com.back_alasso.features.User.User;
import com.back_alasso.features.User.UserService;
import com.back_alasso.features.Voluntary.Voluntary;
import com.back_alasso.features.Voluntary.VoluntaryService;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityVoluntaryService {

    private final ActivityRepository activityRepository;
    private final ActivityVoluntaryRepository activityVoluntaryRepository;
    private final ActivityService activityService;
    private final VoluntaryService voluntaryService;
    private final MailService mailService;
    private final UserService userService;

    @Value("${custom.client-url}")
    private String clientUrl;

    public ActivityParticipantsRequestDTO getActivityVoluntary(UUID activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResourceNotFoundException("Activity not found"));

        return ActivityParticipantsRequestDTO.convertToDTO(activity);
    }

    public ActivityVoluntary getActivityVoluntaryByIds(UUID authenticatedUser, UUID activityId) {
        return activityVoluntaryRepository.findByVoluntary_idAndActivity_id(authenticatedUser, activityId).orElse(null);
    }

    public Boolean updatedFavoriteStatus(UUID activityId, boolean isFavorite, UUID authenticatedUserId) {
        ActivityVoluntary activityVoluntary = getActivityVoluntaryByIds(authenticatedUserId, activityId);
        if (activityVoluntary != null) {
            activityVoluntary.setSaved(isFavorite);
            activityVoluntaryRepository.save(activityVoluntary);

            return isFavorite;
        } else {
            Voluntary voluntary = voluntaryService.getVoluntaryById(authenticatedUserId);
            Activity activity = activityService.getActivityById(activityId);
            ActivityVoluntary newActivityVoluntary = new ActivityVoluntary(isFavorite, false, voluntary, activity);
            activityVoluntaryRepository.save(newActivityVoluntary);
            return isFavorite;
        }
    }

    public Boolean updatedRegisterStatus(UUID activityId, boolean isRegistered, UUID authenticatedUserId) {
        ActivityVoluntary activityVoluntary = getActivityVoluntaryByIds(authenticatedUserId, activityId);
        User user = userService.findById(authenticatedUserId);
        Activity activity = activityService.getActivityById(activityId);

        String subject;
        String body;

        if (activity.getDate().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Impossible de modifier l'inscription : activité déjà passée.");
        }

        if (isRegistered) {
            int registeredCount = activityVoluntaryRepository.countByActivity_id(activityId);
            if (registeredCount >= activity.getVoluntariesRequest()) {
                throw new IllegalStateException("Impossible de s'inscrire : activité complète.");
            }
        }


        if (activityVoluntary != null) {
            activityVoluntary.setRegistered(isRegistered);
            activityVoluntaryRepository.save(activityVoluntary);

            subject = isRegistered ? "🎉 Inscription confirmée" : "❌ Désinscription confirmée";
        } else {
            Voluntary voluntary = voluntaryService.getVoluntaryById(authenticatedUserId);
            ActivityVoluntary newActivityVoluntary = new ActivityVoluntary(false, isRegistered, voluntary, activity);
            activityVoluntaryRepository.save(newActivityVoluntary);

            subject = "🎉 Inscription confirmée";
        }

        String actionText = isRegistered ? "Vous êtes bien inscrit(e) à l'activité" : "Votre désinscription à l'activité a bien été prise en compte";

        String formattedDate = activity.getDate().format(java.time.format.DateTimeFormatter.ofPattern("dd MMMM yyyy 'à' HH:mm"));
        String activityUrl = clientUrl + "/activities/" + activity.getId();
        String address = "Adresse à confirmer";
        if (activity.getAddress() != null) {
            Address addr = activity.getAddress();
            address = String.format(
                    "%s %s, %s %s",
                    addr.getHouse_number() != null ? addr.getHouse_number() : "",
                    addr.getStreet_name() != null ? addr.getStreet_name() : "",
                    addr.getZipCode() != null ? addr.getZipCode() : "",
                    addr.getCity() != null ? addr.getCity() : ""
            ).trim();
        }
        body = String.format(
                """
                        Bonjour %s,
                        
                        %s :
                        
                        📌 Activité : %s
                        📅 Date : %s
                        🏢 Association : %s
                        📍 Lieu : %s
                        
                        👉 Plus d'infos ici : %s
                        
                        À bientôt !
                        L'équipe A-L'Asso
                        """,
                user.getUsername(),
                actionText,
                activity.getTitle(),
                formattedDate,
                activity.getAssociation().getName(),
                address,
                activityUrl
        );

        mailService.buildMail(user.getEmail(), subject, body);
        return isRegistered;
    }
}

