package com.back_alasso.Voluntary;

import com.back_alasso.AssociationFollower.AssociationFollower;
import com.back_alasso.Country.Country;
import com.back_alasso.Image.Image;
import com.back_alasso.User.AccountEnumType;
import com.back_alasso.User.User;
import com.back_alasso.User.UserEnumType;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Voluntary extends User {

  public static final int CITY_MAX_LENGTH = 200;
  public static final int NAME_MAX_LENGTH = 50;

  @Column(nullable = false, length = CITY_MAX_LENGTH)
  private String city;

  @Column(nullable = false, length = NAME_MAX_LENGTH)
  private String first_name;

  @Column(nullable = false, length = NAME_MAX_LENGTH)
  private String last_name;

  @ManyToOne
  @JoinColumn(name = "country_id")
  private Country country;

  @ManyToOne
  @JoinColumn(name = "image_id")
  private Image avatar;

  @OneToMany(mappedBy = "userVoluntary")
  private List<AssociationFollower> associationFollowers;

  public Voluntary(
    UserEnumType user_type,
    AccountEnumType account_status,
    String hashed_password,
    String email,
    String city,
    String first_name,
    String last_name,
    Country country,
    Image avatar,
    List<AssociationFollower> associationFollowers
  ) {
    super(user_type, account_status, hashed_password, email);
    this.city = city;
    this.first_name = first_name;
    this.last_name = last_name;
    this.country = country;
    this.avatar = avatar;
    this.associationFollowers = associationFollowers;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public Image getAvatar() {
    return avatar;
  }

  public void setAvatar(Image avatar) {
    this.avatar = avatar;
  }

  public List<AssociationFollower> getAssociationFollowers() {
    return associationFollowers;
  }

  public void setAssociationFollowers(List<AssociationFollower> associationFollowers) {
    this.associationFollowers = associationFollowers;
  }
}
