package com.back_alasso.Association;


import com.back_alasso.AssociationFollower.AssociationFollower;

import com.back_alasso.Image.ImageEnumType;

import com.back_alasso.Statistic.StatisticDTO;


import java.time.LocalDate;
import java.util.List;

public record AssociationCardDTO(
        String description,
        String founder,
        LocalDate foundationDate,
        String name,
        String associationProfileImageURL,
        String associationLogoImage,
        String siteURL,
        List<StatisticDTO> statistics,
        Boolean isFollow

) {

    public static AssociationCardDTO fromEntityToDTO(Association association) {
        return new AssociationCardDTO(
                association.getDescription(),
                association.getFounder(),
                association.getFoundationDate(),
                association.getName(),
                association.getAssociationImages().stream()
                        .filter(associationImage -> associationImage.getImage().getType() == ImageEnumType.PROFILE_ASSOCIATION)
                        .map(associationImage -> associationImage.getImage().getUrl())
                        .findFirst()
                        .orElse("defaultAssociationProfileImage.png"),
                association.getAssociationImages().stream()
                        .filter(associationImage -> associationImage.getImage().getType() == ImageEnumType.LOGO)
                        .map(associationImage -> associationImage.getImage().getUrl())
                        .findFirst()
                        .orElse("defaultAssociationProfileImage.png"),
                association.getSiteURL(),
                association.getStatistic().stream().map(StatisticDTO::fromEntityToDTO).toList(),
                association.getAssociationFollowers().stream().filter(associationFollower ->  {
                    String authenticatedUserId = "TODO --> "; // getUserAuthentificated();
                    if(authenticatedUserId == null) {
                        return false;
                    }
                    return associationFollower.getVoluntary().getId().equals(authenticatedUserId);
                        }).findFirst().map(AssociationFollower::isIs_follow).orElse(false));
    }

}
