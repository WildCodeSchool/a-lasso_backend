package com.back_alasso.config;

import com.back_alasso.Address.Address;
import com.back_alasso.Address.AddressRepository;
import com.back_alasso.Association.Association;
import com.back_alasso.Association.AssociationRepository;
import com.back_alasso.AssociationImage.AssociationImage;
import com.back_alasso.AssociationImage.AssociationImageRepository;
import com.back_alasso.Country.Country;
import com.back_alasso.Country.CountryRepository;
import com.back_alasso.Image.Image;
import com.back_alasso.Image.ImageEnumType;
import com.back_alasso.Image.ImageRepository;
import com.back_alasso.User.AccountEnumType;
import com.back_alasso.User.UserEnumType;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

  // const
  public static final int FIRST_HOUSE_NUMBER = 7;
  public static final int FIRST_ASSO_YEAR_FOUNDED = 1864;
  public static final int FIRST_ASSO_MONTH_FOUNDED = 8;
  public static final int FIRST_ASSO_DAY_FOUNDED = 25;

  public static final int NAME_MAX_LENGTH = 50;

  private final CountryRepository countryRepository;
  private final AddressRepository addressRepository;
  private final AssociationRepository associationRepository;
  private final AssociationImageRepository associationImageRepository;
  private final ImageRepository imageRepository;

  public DatabaseInitializer(
    CountryRepository countryRepository,
    AddressRepository addressRepository,
    AssociationRepository associationRepository,
    AssociationImageRepository associationImageRepository,
    ImageRepository imageRepository
  ) {
    this.countryRepository = countryRepository;
    this.addressRepository = addressRepository;
    this.associationRepository = associationRepository;
    this.associationImageRepository = associationImageRepository;
    this.imageRepository = imageRepository;
  }

  @Bean
  CommandLineRunner init() {
    Country firstCountry = new Country("France");

    Address firstAddress = new Address(FIRST_HOUSE_NUMBER, "rue de l'industrie", null, "44120", "VERTOU", firstCountry);

    Image laCroixRougePicture1 = new Image("/images/LaCroixRougePicture1.jpg", ImageEnumType.PROFILE_ASSOCIATION);

    Association fristAssociation = new Association(
      "Une organisation pour aider ceux dans le besoin",
      "Henry DUNANT",
      LocalDate.of(FIRST_ASSO_YEAR_FOUNDED, FIRST_ASSO_MONTH_FOUNDED, FIRST_ASSO_DAY_FOUNDED),
      "LA CROIX ROUGE",
      firstAddress,
      null,
      UserEnumType.ASSOCIATION,
      AccountEnumType.ACTIVE,
      "hashed_password",
      "lacroixrouge@gmail.com",
      null
    );

    AssociationImage laCroixRougeImages = new AssociationImage(laCroixRougePicture1, fristAssociation);

    return args -> {
      countryRepository.save(firstCountry);
      addressRepository.save(firstAddress);
      imageRepository.save(laCroixRougePicture1);
      associationRepository.save(fristAssociation);
      associationImageRepository.save(laCroixRougeImages);
    };
  }
}
