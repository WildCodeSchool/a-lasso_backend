package com.back_alasso.AssociationImage;

import com.back_alasso.Association.Association;
import com.back_alasso.Image.Image;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AssociationImage extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "image_id")
  private Image image;

  @ManyToOne
  @JoinColumn(name = "association_id")
  private Association association;

  public AssociationImage(Image image, Association association) {
    this.image = image;
    this.association = association;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public Association getAssociation() {
    return association;
  }

  public void setAssociation(Association association) {
    this.association = association;
  }
}
