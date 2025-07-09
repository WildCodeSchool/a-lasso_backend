package com.back_alasso.AssociationImage;

import com.back_alasso.Association.Association;
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
public class AssociationImage extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "image_id")
  private Image image;

  @ManyToOne
  @JoinColumn(name = "association_id")
  private Association association;
}
