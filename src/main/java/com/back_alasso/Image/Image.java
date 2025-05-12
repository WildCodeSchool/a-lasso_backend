package com.back_alasso.Image;

import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Image extends BaseEntity {

  @Column(nullable = false)
  private String url;

  @Lob
  @Column(name = "data", columnDefinition = "LONGBLOB")
  private byte[] data; // For blob storage

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ImageEnumType type;

  // Necessary to have an empty constructor to instance object.
  public Image() {}

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

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }
}
