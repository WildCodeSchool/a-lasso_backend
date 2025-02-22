package com.back_alasso.config;

import com.back_alasso.Activity.Activity;
import com.back_alasso.Activity.ActivityRepository;
import com.back_alasso.ActivityImage.ActivityImage;
import com.back_alasso.ActivityImage.ActivityImageRepository;
import com.back_alasso.ActivityTheme.ActivityTheme;
import com.back_alasso.ActivityTheme.ActivityThemeRepository;
import com.back_alasso.Address.Address;
import com.back_alasso.Address.AddressRepository;
import com.back_alasso.Association.Association;
import com.back_alasso.Association.AssociationRepository;
import com.back_alasso.AssociationImage.AssociationImage;
import com.back_alasso.AssociationImage.AssociationImageRepository;
import com.back_alasso.Country.Country;
import com.back_alasso.Country.CountryRepository;
import com.back_alasso.Geolocalisation.Geolocalisation;
import com.back_alasso.Geolocalisation.GeolocalisationRepository;
import com.back_alasso.Image.Image;
import com.back_alasso.Image.ImageEnumType;
import com.back_alasso.Image.ImageRepository;
import com.back_alasso.Preferences.Preferences;
import com.back_alasso.Preferences.PreferencesRepository;
import com.back_alasso.Theme.Theme;
import com.back_alasso.Theme.ThemeNameEnumType;
import com.back_alasso.Theme.ThemeRepository;
import com.back_alasso.User.AccountEnumType;
import com.back_alasso.User.UserEnumType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

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

    public static final double FIRST_ASSO_LONGITUDE = -1.4693;
    public static final double FIRST_ASSO_LATITUDE = 47.1687;
    public static final double FIRST_ACTIVITY_LATITUDE = 47.2173;
    public static final double FIRST_ACTIVITY_LONGITUDE = -1.5534;
    public static final long FIRST_ACTIVITY_VOLONTARY_REQUEST = 10;

    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final AssociationRepository associationRepository;
    private final AssociationImageRepository associationImageRepository;
    private final ImageRepository imageRepository;
    private final GeolocalisationRepository geolocalisationRepository;
    private final ThemeRepository themeRepository;
    private final PreferencesRepository preferencesRepository;
    private final ActivityImageRepository activityImageRepository;
    private final ActivityRepository activityRepository;
    private final ActivityThemeRepository activityThemeRepository;

    public DatabaseInitializer(
            CountryRepository countryRepository,
            AddressRepository addressRepository,
            AssociationRepository associationRepository,
            AssociationImageRepository associationImageRepository,
            ImageRepository imageRepository,
            GeolocalisationRepository geolocalisationRepository,
            ThemeRepository themeRepository,
            PreferencesRepository preferencesRepository,
            ActivityImageRepository activityImageRepository,
            ActivityRepository activityRepository,
            ActivityThemeRepository activityThemeRepository
    ) {
        this.countryRepository = countryRepository;
        this.addressRepository = addressRepository;
        this.associationRepository = associationRepository;
        this.associationImageRepository = associationImageRepository;
        this.imageRepository = imageRepository;
        this.geolocalisationRepository = geolocalisationRepository;
        this.themeRepository = themeRepository;
        this.preferencesRepository = preferencesRepository;
        this.activityImageRepository = activityImageRepository;
        this.activityRepository = activityRepository;
        this.activityThemeRepository = activityThemeRepository;
    }

    @Bean
    CommandLineRunner init() {
        // initiate general data which doesn't change.
        Country firstCountry = new Country("France");
        List<Theme> allThemesForActivity = Arrays.asList(
                new Theme(ThemeNameEnumType.Santé, "/images/Activity/Themes/Theme_Sante.png"),
                new Theme(ThemeNameEnumType.Nature, "/images/Activity/Themes/Theme_Nature.png"),
                new Theme(ThemeNameEnumType.Cours, "/images/Activity/Themes/Theme_Cours.png"),
                new Theme(ThemeNameEnumType.Culture, "/images/Activity/Themes/Theme_Culture.png"),
                new Theme(ThemeNameEnumType.Culinaire, "/images/Activity/Themes/Theme_Culinaire.png"),
                new Theme(ThemeNameEnumType.Social, "/images/Activity/Themes/Theme_Social.png"),
                new Theme(ThemeNameEnumType.Sport, "/images/Activity/Themes/Theme_Sport.png")
        );

        // initiate example of one association : "La Croix Rouge"
        Address firstAddress = new Address(FIRST_HOUSE_NUMBER, "rue de l'industrie", null, "44120", "VERTOU", firstCountry);
        Image laCroixRougeProfileImage = new Image("/images/Association/croixRouge.png", ImageEnumType.PROFILE_ASSOCIATION);
        Image laCroixRougeLogo = new Image("/images/Association/logoCroixRouge.png", ImageEnumType.LOGO);

        Association firstAssociation = new Association(
                "La Croix-Rouge française agit pour protéger et relever sans condition, les personnes en situation de vulnérabilité et construire avec elles leur résilience.",
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

        Preferences laCroixRougePreferences = new Preferences(firstAssociation);
        Geolocalisation laCroixRougeLocalisation = new Geolocalisation(FIRST_ASSO_LONGITUDE, FIRST_ASSO_LATITUDE);
        laCroixRougeLocalisation.addUser(firstAssociation);

        List<AssociationImage> laCroixRougeAssociationImages = Arrays.asList(
                new AssociationImage(laCroixRougeProfileImage, firstAssociation),
                new AssociationImage(laCroixRougeLogo, firstAssociation)
        );

        // initiate example of one activity : "La Maraude de La Croix Rouge"
        Image maraudeOne = new Image("/images/Association/maraude1.png", ImageEnumType.ACTIVITY);
        Image maraudeTwo = new Image("/images/Association/maraude2.png", ImageEnumType.ACTIVITY);
        Address maraudeAddress = new Address(FIRST_HOUSE_NUMBER, "rue du bonheur", null, "44300", "NANTES", firstCountry);

        Activity maraudeActivity = new Activity("La maraude",
                LocalDateTime.parse("2025-12-22 04:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                "Vous souhaitez vous engager pour une société plus humaine et solidaire ? Vous êtes sensible aux besoins des personnes sans-abri ?\n" +
                        "\n" +
                        "\n" +
                        "Participez à des maraudes pour créer du lien social avec les personnes sans-abri. Au sein d'une équipe, partez à la rencontre de ces \"invisibles\" pour le temps d’une soirée, leur apporter votre soutien, distribuer des cafés, des couvertures et des sourires, et préserver ainsi leur dignité et favoriser leur retour à l'autonomie.",
                FIRST_ACTIVITY_VOLONTARY_REQUEST,
                firstAssociation,
                maraudeAddress,
                null,
                null
        );

        List<ActivityImage> maraudeActivityImages = Arrays.asList(new ActivityImage(maraudeOne, maraudeActivity),
                new ActivityImage(maraudeTwo, maraudeActivity));


        Geolocalisation maraudeLocalisation = new Geolocalisation(FIRST_ACTIVITY_LONGITUDE, FIRST_ACTIVITY_LATITUDE);
        maraudeLocalisation.addActivity(maraudeActivity);

        return args -> {
            // save general data
            countryRepository.save(firstCountry);
            themeRepository.saveAll(allThemesForActivity);

            Theme socialTheme = themeRepository.findByName(ThemeNameEnumType.Social);
            Theme santeTheme = themeRepository.findByName(ThemeNameEnumType.Santé);

            // save association : "La croix rouge"
            addressRepository.save(firstAddress);
            imageRepository.save(laCroixRougeProfileImage);
            imageRepository.save(laCroixRougeLogo);
            associationRepository.save(firstAssociation);
            associationImageRepository.saveAll(laCroixRougeAssociationImages);
            geolocalisationRepository.save(laCroixRougeLocalisation);
            preferencesRepository.save(laCroixRougePreferences);

            // save activity : "La Maraude de la Croix Rouge"
            imageRepository.save(maraudeOne);
            imageRepository.save(maraudeTwo);
            activityRepository.save(maraudeActivity);

            List<ActivityTheme> maraudeActivityThemes = Arrays.asList(
                    new ActivityTheme(maraudeActivity, socialTheme),
                    new ActivityTheme(maraudeActivity, santeTheme)
            );

            activityThemeRepository.saveAll(maraudeActivityThemes);
            activityImageRepository.saveAll(maraudeActivityImages);
            geolocalisationRepository.save(maraudeLocalisation);

            // save voluntary

        };
    }
}
