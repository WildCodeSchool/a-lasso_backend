package com.back_alasso.Activity;

import com.back_alasso.ActivityImage.ActivityImage;
import java.util.Base64;
import java.util.List;

public class ActivityImageMapper {

  public static List<ActivityImagesResponseDTO> toResponseDTOs(List<ActivityImage> images) {
    if (images == null) return null;

    return images
      .stream()
      .map(image -> {
        if (image.getImage() == null) {
          return new ActivityImagesResponseDTO(null, null);
        }

        byte[] data = image.getImage().getData();
        String base64 = data != null ? "data:image/png;base64," + Base64.getEncoder().encodeToString(data) : null;

        String url = image.getImage().getUrl();

        return new ActivityImagesResponseDTO(url, base64);
      })
      .toList();
  }
}
