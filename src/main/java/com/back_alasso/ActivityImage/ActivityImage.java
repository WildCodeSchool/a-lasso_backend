package com.back_alasso.ActivityImage;

import com.back_alasso.Activity.Activity;
import com.back_alasso.Image.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ActivityImage {

  @ManyToOne
  @JoinColumn(name = "image_id")
  private Image image;

  @ManyToOne
  @JoinColumn(name = "activity_id")
  private Activity activity;

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public Activity getActivity() {
    return activity;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }
}
