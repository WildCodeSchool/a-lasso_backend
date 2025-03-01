package com.back_alasso.Country;

import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Country extends BaseEntity {

  // Attributes
  public static final int NAME_MAX_LENGTH = 100;

  @Column(nullable = false, length = NAME_MAX_LENGTH)
  private String name;

  // Constructor
  // Necessary to have an empty constructor to instance object.
  public Country() {}

  public Country(String name) {
    this.name = name;
  }

  // Getters & setters
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
