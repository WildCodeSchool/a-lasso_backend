package com.back_alasso.ActivityImage;

import com.back_alasso.Activity.Activity;
import com.back_alasso.Image.Image;
import com.back_alasso.core.BaseEntity;
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
