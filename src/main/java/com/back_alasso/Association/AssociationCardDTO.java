package com.back_alasso.Association;

import com.back_alasso.AssociationFollower.AssociationFollower;
import com.back_alasso.Image.ImageEnumType;
import com.back_alasso.Statistic.StatisticDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AssociationCardDTO(
        UUID id,
        String description,
        String founder,
        LocalDate foundationDate,
        String name,
        String associationProfileImageURL,
        String associationLogoImage,
        String siteURL,
        List<StatisticDTO> statistics
) {
    public static AssociationCardDTO fromEntityToDTO(Association association, UUID authenticatedUserId) {
        return new AssociationCardDTO(
                association.getId(),
                association.getDescription(),
                association.getFounder(),
                association.getFoundationDate(),
                association.getName(),
                association
                        .getAssociationImages()
                        .stream()
                        .filter(associationImage -> associationImage.getImage().getType() == ImageEnumType.PROFILE_ASSOCIATION)
                        .map(associationImage -> associationImage.getImage().getUrl())
                        .findFirst()
                        .orElse("defaultAssociationProfileImage.png"),
                association
                        .getAssociationImages()
                        .stream()
                        .filter(associationImage -> associationImage.getImage().getType() == ImageEnumType.LOGO)
                        .map(associationImage -> associationImage.getImage().getUrl())
                        .findFirst()
                        .orElse("defaultAssociationProfileImage.png"),
                association.getSiteURL(),
                association.getStatistic().stream().map(StatisticDTO::fromEntityToDTO).toList()
        );
    }

    private static boolean getIsFollow(UUID authenticatedUserId, Association association) {
        if (authenticatedUserId == null) {
            return false;
        }

        return association
                .getAssociationFollowers()
                .stream()
                .filter(associationFollower -> associationFollower.getVoluntary().getId().equals(authenticatedUserId))
                .findFirst()
                .map(AssociationFollower::isFollow)
                .orElse(false);
    }
}
