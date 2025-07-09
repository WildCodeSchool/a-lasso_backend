package com.back_alasso.Country;

import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country extends BaseEntity {

  public static final int NAME_MAX_LENGTH = 100;

  @Column(nullable = false, length = NAME_MAX_LENGTH)
  private String name;
}
