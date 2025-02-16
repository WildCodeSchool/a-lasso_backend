package com.back_alasso.Activity;

import com.back_alasso.ActivityVoluntary.ActivityVoluntary;
import com.back_alasso.ActivityVoluntary.ActivityVoluntaryDTO;
import com.back_alasso.Association.AssociationDTO;
import com.back_alasso.Theme.ThemeNameEnumType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record ActivityDTO(
        UUID id,
        String title,
        String description,
        List<String> image,
        AssociationDTO association,
        String location,
        Date date,
        ActivityVoluntaryDTO participants,
        List<ThemeNameEnumType> theme,
        boolean isSaved
) {
    private static final Logger log = LoggerFactory.getLogger(ActivityDTO.class);


    public static ActivityDTO fromEntityToDTO(Activity activity) {
        return new ActivityDTO(
                activity.getId(),
                activity.getTitle(),
                activity.getDescription(),
                activity.getActivityImages() != null ? activity.getActivityImages().stream().map(
                        (activityImage) -> activityImage.getImage().getUrl()).toList() : null,
                activity.getAssociation() != null ? AssociationDTO.getAssociationDTO(activity.getAssociation()) : null,
                activity.getAddress().getCity(),
                activity.getDate(),
                ActivityVoluntaryDTO.convertToDTO(activity),
                activity.getActivityThemes().stream().map((activityTheme) -> activityTheme.getTheme().getName()).toList(),
                activity.getActivityVoluntaries().stream()
                        .filter(activityVoluntary -> {
                            // Récupérer l'utilisateur authentifié
                            String authenticatedUserId = "TODO -->"; // getUserAuthentificated();

                            // Vérifier si l'utilisateur est connecté
                            if (authenticatedUserId == null) {
                                return false; // L'utilisateur n'est pas connecté → retour false
                            }

                            // Comparer l'ID de l'utilisateur authentifié avec l'ID du volontaire
                            return activityVoluntary.getVoluntary().getId().equals(authenticatedUserId);
                        })
                        .findFirst()
                        .map(ActivityVoluntary::isIs_saved) // Si trouvé, récupérer isIs_saved
                        .orElse(false) // Si aucun résultat, retour false
        );
    }
}


