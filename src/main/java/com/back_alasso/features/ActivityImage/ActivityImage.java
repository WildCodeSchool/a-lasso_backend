package com.back_alasso.features.ActivityImage;

import com.back_alasso.core.BaseEntity;
import com.back_alasso.features.Activity.Activity;
import com.back_alasso.features.Image.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityImage extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "image_id")
  private Image image;

  @ManyToOne
  @JoinColumn(name = "activity_id")
  private Activity activity;
}
