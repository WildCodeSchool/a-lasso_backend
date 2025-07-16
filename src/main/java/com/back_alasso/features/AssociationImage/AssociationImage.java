package com.back_alasso.features.AssociationImage;

import com.back_alasso.core.BaseEntity;
import com.back_alasso.features.Association.Association;
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
public class AssociationImage extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "image_id")
  private Image image;

  @ManyToOne
  @JoinColumn(name = "association_id")
  private Association association;
}
