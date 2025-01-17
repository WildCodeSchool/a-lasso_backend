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
}
