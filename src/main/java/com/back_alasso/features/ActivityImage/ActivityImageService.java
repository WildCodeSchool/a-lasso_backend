package com.back_alasso.features.ActivityImage;

import com.back_alasso.features.Activity.Activity;
import com.back_alasso.features.Image.Image;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ActivityImageService {

  public void linkImagesToActivity(Activity activity, List<Image> images) {
    if (images == null) return;

    // Remove only ActivityImages that are not in the new images list
    List<ActivityImage> toRemove = new ArrayList<>();
    for (ActivityImage ai : activity.getActivityImages()) {
      if (images.stream().noneMatch(img -> img.getId().equals(ai.getImage().getId()))) {
        toRemove.add(ai);
      }
    }

    activity.getActivityImages().removeAll(toRemove);

    // Add new ActivityImages for images not already linked
    for (Image image : images) {
      boolean alreadyLinked = activity.getActivityImages().stream().anyMatch(ai -> ai.getImage().getId().equals(image.getId()));
      if (!alreadyLinked) {
        activity.getActivityImages().add(new ActivityImage(image, activity));
      }
    }
  }
}
