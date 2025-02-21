package com.back_alasso.Image;

import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Image extends BaseEntity {

  @Column(nullable = false)
  private String url;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ImageEnumType type;

  public Image(String url, ImageEnumType type) {
    this.url = url;
    this.type = type;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public ImageEnumType getType() {
    return type;
  }

  public void setType(ImageEnumType type) {
    this.type = type;
  }
}
