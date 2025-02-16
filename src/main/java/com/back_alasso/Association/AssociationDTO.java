package com.back_alasso.Association;

import java.util.stream.Collectors;

public record AssociationDTO(String name, String logo, boolean isFollow) {

    public static AssociationDTO getAssociationDTO(Association association) {
        return new AssociationDTO(
                association.getName(),
                association.getAssociationImages().stream()
                        .filter((i) -> i.getImage().getType().name().equals("logo"))
                        .map((i) -> i.getImage().getUrl())
                        .collect(Collectors.joining()),
                association.getAssociationFollowers().stream()
                        .anyMatch(a -> a.getUserAssociation().getId().equals(association.getId()) && a.isIs_follow())
        );
    }
}

