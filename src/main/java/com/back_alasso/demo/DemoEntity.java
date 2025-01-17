package com.back_alasso.demo;

import com.back_alasso.core.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DemoEntity extends BaseEntity {

  private String description;

  public DemoEntity(String description) {
    this.description = description;
  }
}
