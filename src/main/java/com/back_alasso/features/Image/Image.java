package com.back_alasso.features.Image;

import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image extends BaseEntity {

  @Column(nullable = false)
  private String url;

  @Column(nullable = true)
  private String filename;

  @Column(nullable = true)
  private String format;

  @Lob
  @Column(name = "data", columnDefinition = "LONGBLOB")
  private byte[] data; // For blob storage

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ImageEnumType type;

  public Image(String url, ImageEnumType type) {
    this.url = url;
    this.type = type;
  }
}
