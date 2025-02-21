package com.back_alasso.Association;

import com.back_alasso.Address.Address;
import com.back_alasso.AssociationFollower.AssociationFollower;
import com.back_alasso.AssociationImage.AssociationImage;
import com.back_alasso.User.AccountEnumType;
import com.back_alasso.User.User;
import com.back_alasso.User.UserEnumType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Association extends User {

  public static final int DESC_MAX_LENGTH = 500;
  public static final int FOUNDER_MAX_LENGTH = 50;
  public static final int NAME_MAX_LENGTH = 50;

  @Column(nullable = true, length = DESC_MAX_LENGTH)
  private String description;

  @Column(nullable = true, length = FOUNDER_MAX_LENGTH)
  private String founder;

  @Column(nullable = true)
  private LocalDate foundationDate;

  @Column(nullable = false, length = NAME_MAX_LENGTH)
  private String name;

  @ManyToOne
  @JoinColumn(name = "adress_id")
  private Address address;

  @OneToMany(mappedBy = "association")
  private List<AssociationImage> associationImages;

  @OneToMany(mappedBy = "userAssociation")
  private List<AssociationFollower> associationFollowers;

  public Association(
    String description,
    String founder,
    LocalDate foundationDate,
    String name,
    Address address,
    List<AssociationImage> associationImages,
    UserEnumType user_type,
    AccountEnumType account_status,
    String hashed_password,
    String email,
    List<AssociationFollower> associationFollowers
  ) {
    super(user_type, account_status, hashed_password, email);
    this.description = description;
    this.founder = founder;
    this.foundationDate = foundationDate;
    this.name = name;
    this.address = address;
    this.associationImages = associationImages;
    this.associationFollowers = associationFollowers;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getFounder() {
    return founder;
  }

  public void setFounder(String founder) {
    this.founder = founder;
  }

  public LocalDate getFoundationDate() {
    return foundationDate;
  }

  public void setFoundationDate(LocalDate foundationDate) {
    this.foundationDate = foundationDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Address getAdress() {
    return address;
  }

  public void setAdress(Address address) {
    this.address = address;
  }

  public List<AssociationImage> getAssociationImages() {
    return associationImages;
  }

  public void setAssociationImages(List<AssociationImage> associationImages) {
    this.associationImages = associationImages;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public List<AssociationFollower> getAssociationFollowers() {
    return associationFollowers;
  }

  public void setAssociationFollowers(List<AssociationFollower> associationFollowers) {
    this.associationFollowers = associationFollowers;
  }
}
