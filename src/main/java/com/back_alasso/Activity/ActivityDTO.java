package com.back_alasso.Activity;

import com.back_alasso.Theme.ThemeNameEnumType;
import java.util.Date;
import java.util.List;
import java.util.UUID;

record Organization(String name, String logo, boolean isFollow) {}

record Participant(int current, int max) {}

public record ActivityDTO(
  UUID id,
  String title,
  String description,
  List image,
  Organization organization,
  String location,
  Date date,
  Participant participants,
  ThemeNameEnumType[] theme,
  boolean isFavorite
) {
  //    public static ActivityDTO fromEntityToDTO(Activity activity) {
  ////        return new ActivityDTO(
  ////                activity.getId(),
  ////                activity.getTitle(),
  ////                activity.getDescription(),
  ////                activity.getActivityImages() != null ? activity.getActivityImages().stream().map((activityImage) -> activityImage.getImage().getUrl()).toList() : null,
  ////                activity.getCategory() != null ? article.getCategory().getName() : null,
  ////                activity.getArticleAuthors() != null ? article.getArticleAuthors().stream().map((i) -> AuthorDTO.mapFromEntity(i.getAuthor())).toList() : null
  ////        );
  //    }
}
