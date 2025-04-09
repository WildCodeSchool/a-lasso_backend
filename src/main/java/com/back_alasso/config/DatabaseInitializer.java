package com.back_alasso.config;

import com.back_alasso.Activity.Activity;
import com.back_alasso.Activity.ActivityRepository;
import com.back_alasso.ActivityImage.ActivityImage;
import com.back_alasso.ActivityImage.ActivityImageRepository;
import com.back_alasso.ActivityTheme.ActivityTheme;
import com.back_alasso.ActivityTheme.ActivityThemeRepository;
import com.back_alasso.ActivityVoluntary.ActivityVoluntary;
import com.back_alasso.ActivityVoluntary.ActivityVoluntaryRepository;
import com.back_alasso.Address.Address;
import com.back_alasso.Address.AddressRepository;
import com.back_alasso.Association.Association;
import com.back_alasso.Association.AssociationRepository;
import com.back_alasso.AssociationFollower.AssociationFollower;
import com.back_alasso.AssociationFollower.AssociationFollowerRepository;
import com.back_alasso.AssociationImage.AssociationImage;
import com.back_alasso.AssociationImage.AssociationImageRepository;
import com.back_alasso.Country.Country;
import com.back_alasso.Country.CountryRepository;
import com.back_alasso.Geolocation.Geolocation;
import com.back_alasso.Geolocation.GeolocationRepository;
import com.back_alasso.Image.Image;
import com.back_alasso.Image.ImageEnumType;
import com.back_alasso.Image.ImageRepository;
import com.back_alasso.Message.Message;
import com.back_alasso.Message.MessageRepository;
import com.back_alasso.Preferences.Preferences;
import com.back_alasso.Preferences.PreferencesRepository;
import com.back_alasso.Report.ReasonReportEnumType;
import com.back_alasso.Report.Report;
import com.back_alasso.Report.ReportRepository;
import com.back_alasso.Report.StatusReportEnumType;
import com.back_alasso.Statistic.Statistic;
import com.back_alasso.Statistic.StatisticRepository;
import com.back_alasso.Theme.Theme;
import com.back_alasso.Theme.ThemeNameEnumType;
import com.back_alasso.Theme.ThemeRepository;
import com.back_alasso.User.AccountEnumType;
import com.back_alasso.User.UserEnumType;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.Voluntary.VoluntaryRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

  public static final int FIRST_HOUSE_NUMBER = 10;
  public static final int SECOND_HOUSE_NUMBER = 61;
  public static final int FIRST_ASSO_YEAR_FOUNDED = 1864;
  public static final int FIRST_ASSO_MONTH_FOUNDED = 8;
  public static final int FIRST_ASSO_DAY_FOUNDED = 25;
  public static final int SECOND_ASSO_YEAR_FOUNDED = 1845;
  public static final int YEAR_MESSAGE = 2025;
  public static final int DAY_MESSAGE_ONE = 10;
  public static final int DAY_MESSAGE_TWO = 11;

  public static final int SECOND_ASSO_MONTH_FOUNDED = 12;
  public static final int SECOND_ASSO_DAY_FOUNDED = 2;
  public static final double FIRST_ASSO_LONGITUDE = -1.5043288;
  public static final double FIRST_ASSO_LATITUDE = 47.2492735;
  public static final double SECOND_ASSO_LONGITUDE = -1.418277;
  public static final double SECOND_ASSO_LATITUDE = 47.3162423;
  public static final int FIRST_ASSO_FIRST_STATS = 70521;
  public static final int FIRST_ASSO_SECOND_STATS = 64430;
  public static final int FIRST_ASSO_THIRD_STATS = 10446;
  public static final int SECOND_ASSO_FIRST_STATS = 64;
  public static final int SECOND_ASSO_SECOND_STATS = 5127;
  public static final int SECOND_ASSO_THIRD_STATS = 45000;

  public static final double FIRST_ACTIVITY_LATITUDE = 47.2173;
  public static final double FIRST_ACTIVITY_LONGITUDE = -1.5534;
  public static final long FIRST_ACTIVITY_VOLONTARY_REQUEST = 10;
  public static final long SECOND_ACTIVITY_VOLONTARY_REQUEST = 20;
  public static final int ACTIVITY_THEME_NUMBER = 5;

  public static final int NUMBER_TWO = 2;
  public static final int NUMBER_THREE = 3;

  public static final double FIRST_VOLUNTARY_LATITUDE = 47.218637;
  public static final double FIRST_VOLUNTARY_LONGITUDE = -1.554136;

  private final CountryRepository countryRepository;
  private final AddressRepository addressRepository;
  private final AssociationRepository associationRepository;
  private final AssociationImageRepository associationImageRepository;
  private final ImageRepository imageRepository;
  private final GeolocationRepository geolocationRepository;
  private final ThemeRepository themeRepository;
  private final PreferencesRepository preferencesRepository;
  private final ActivityImageRepository activityImageRepository;
  private final ActivityRepository activityRepository;
  private final ActivityThemeRepository activityThemeRepository;
  private final StatisticRepository statisticRepository;
  private final VoluntaryRepository voluntaryRepository;
  private final AssociationFollowerRepository associationFollowerRepository;
  private final ActivityVoluntaryRepository activityVoluntaryRepository;
  private final MessageRepository messageRepository;
  private final ReportRepository reportRepository;

  public DatabaseInitializer(
    CountryRepository countryRepository,
    AddressRepository addressRepository,
    AssociationRepository associationRepository,
    AssociationImageRepository associationImageRepository,
    ImageRepository imageRepository,
    GeolocationRepository geolocationRepository,
    ThemeRepository themeRepository,
    PreferencesRepository preferencesRepository,
    ActivityImageRepository activityImageRepository,
    ActivityRepository activityRepository,
    ActivityThemeRepository activityThemeRepository,
    StatisticRepository statisticRepository,
    VoluntaryRepository voluntaryRepository,
    AssociationFollowerRepository associationFollowerRepository,
    ActivityVoluntaryRepository activityVoluntaryRepository,
    MessageRepository messageRepository,
    ReportRepository reportRepository
  ) {
    this.countryRepository = countryRepository;
    this.addressRepository = addressRepository;
    this.associationRepository = associationRepository;
    this.associationImageRepository = associationImageRepository;
    this.imageRepository = imageRepository;
    this.geolocationRepository = geolocationRepository;
    this.themeRepository = themeRepository;
    this.preferencesRepository = preferencesRepository;
    this.activityImageRepository = activityImageRepository;
    this.activityRepository = activityRepository;
    this.activityThemeRepository = activityThemeRepository;
    this.statisticRepository = statisticRepository;
    this.voluntaryRepository = voluntaryRepository;
    this.associationFollowerRepository = associationFollowerRepository;
    this.activityVoluntaryRepository = activityVoluntaryRepository;
    this.messageRepository = messageRepository;
    this.reportRepository = reportRepository;
  }

  @Bean
  CommandLineRunner init() {
    // initiate general data which doesn't change.
    Country firstCountry = new Country("France");
    List<Theme> allThemesForActivity = Arrays.asList(
      new Theme(ThemeNameEnumType.Santé, "fa-solid fa-suitcase-medical"),
      new Theme(ThemeNameEnumType.Nature, "fa-solid fa-tree"),
      new Theme(ThemeNameEnumType.Cours, "fa-solid fa-graduation-cap"),
      new Theme(ThemeNameEnumType.Culture, "fa-solid fa-masks-theater"),
      new Theme(ThemeNameEnumType.Culinaire, "fa-solid fa-bowl-food"),
      new Theme(ThemeNameEnumType.Social, "fa-solid fa-handshake"),
      new Theme(ThemeNameEnumType.Sport, "fa-solid fa-volleyball")
    );

    // initiate examples of associations
    List<Address> associationAddresses = Arrays.asList(
      new Address(FIRST_HOUSE_NUMBER, "rue d'Athènes", null, "44300", "NANTES", firstCountry),
      new Address(SECOND_HOUSE_NUMBER, "chemin de Gralan", null, "44470", "CARQUEFOU", firstCountry)
    );

    List<Image> images = Arrays.asList(
      new Image("/images/Association/croixRouge.png", ImageEnumType.PROFILE_ASSOCIATION),
      new Image("/images/Association/logoCroixRouge.png", ImageEnumType.LOGO),
      new Image("/images/Association/logoSPA.png", ImageEnumType.LOGO),
      new Image("/images/Association/spaProfile.jpg", ImageEnumType.PROFILE_ASSOCIATION)
    );

    List<Association> associations = Arrays.asList(
      new Association(
        "La Croix-Rouge française agit pour protéger et relever sans condition, les personnes en situation de vulnérabilité et construire avec elles leur résilience.",
        "Henry DUNANT",
        LocalDate.of(FIRST_ASSO_YEAR_FOUNDED, FIRST_ASSO_MONTH_FOUNDED, FIRST_ASSO_DAY_FOUNDED),
        "LA CROIX ROUGE",
        associationAddresses.get(0),
        null,
        new HashSet<>(List.of(UserEnumType.ROLE_ASSOCIATION)),
        AccountEnumType.ACTIVE,
        "$2a$10$jw6BeI/txUaC1BQNGYZn4.hs5wpmLhe2uYpTBB40oUveFE3ZRQYQq", // password is "Password"
        "lacroixrouge@gmail.com",
        null,
        "https://www.croix-rouge.fr/"
      ),
      new Association(
        "Depuis 1845, la SPA n’a cessé de protéger les animaux en s’adaptant aux évolutions et aux nouveaux enjeux de la cause et de la société.",
        "Etienne PARISET",
        LocalDate.of(SECOND_ASSO_YEAR_FOUNDED, SECOND_ASSO_MONTH_FOUNDED, SECOND_ASSO_DAY_FOUNDED),
        "S.P.A.",
        associationAddresses.get(1),
        null,
        new HashSet<>(List.of(UserEnumType.ROLE_ASSOCIATION)),
        AccountEnumType.ACTIVE,
        "$2a$10$jw6BeI/txUaC1BQNGYZn4.hs5wpmLhe2uYpTBB40oUveFE3ZRQYQq", // password is "Password"
        "spa@gmail.com",
        null,
        "https://www.la-spa.fr/"
      )
    );

    List<Preferences> associationPreferences = Arrays.asList(new Preferences(associations.get(0)), new Preferences(associations.get(1)));

    List<Geolocation> associationLocalisations = Arrays.asList(
      new Geolocation(FIRST_ASSO_LONGITUDE, FIRST_ASSO_LATITUDE),
      new Geolocation(SECOND_ASSO_LONGITUDE, SECOND_ASSO_LATITUDE)
    );

    associations.get(0).setGeolocation(associationLocalisations.get(0));
    associations.get(1).setGeolocation(associationLocalisations.get(1));

    List<AssociationImage> associationImages = Arrays.asList(
      new AssociationImage(images.get(0), associations.get(0)),
      new AssociationImage(images.get(1), associations.get(0)),
      new AssociationImage(images.get(NUMBER_TWO), associations.get(1)),
      new AssociationImage(images.get(NUMBER_THREE), associations.get(1))
    );

    List<Statistic> associationStatistics = Arrays.asList(
      new Statistic(FIRST_ASSO_FIRST_STATS, "bénévoles", associations.get(0)),
      new Statistic(FIRST_ASSO_SECOND_STATS, "personnes prises en charge", associations.get(0)),
      new Statistic(FIRST_ASSO_THIRD_STATS, "interventions", associations.get(0)),
      new Statistic(SECOND_ASSO_FIRST_STATS, "refuges", associations.get(1)),
      new Statistic(SECOND_ASSO_SECOND_STATS, "bénévoles", associations.get(1)),
      new Statistic(SECOND_ASSO_THIRD_STATS, "animaux pris en charge", associations.get(1))
    );

    // initiate examples of activities
    List<Image> imagesTwo = Arrays.asList(
      new Image("/images/Activity/maraude1.png", ImageEnumType.ACTIVITY),
      new Image("/images/Activity/maraude2.jpg", ImageEnumType.ACTIVITY),
      new Image("/images/Activity/spaActivity.jpg", ImageEnumType.ACTIVITY)
    );

    List<Address> activityAddresses = List.of(new Address(FIRST_HOUSE_NUMBER, "rue du bonheur", null, "44300", "NANTES", firstCountry));

    List<Activity> activities = Arrays.asList(
      new Activity(
        "La maraude",
        LocalDateTime.parse("2025-12-22 04:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        "Vous souhaitez vous engager pour une société plus humaine et solidaire ? Vous êtes sensible aux besoins des personnes sans-abri ?\n" +
        "\n" +
        "\n" +
        "Participez à des maraudes pour créer du lien social avec les personnes sans-abri. Au sein d'une équipe, partez à la rencontre de ces \"invisibles\" pour le temps d’une soirée, leur apporter votre soutien, distribuer des cafés, des couvertures et des sourires, et préserver ainsi leur dignité et favoriser leur retour à l'autonomie.",
        FIRST_ACTIVITY_VOLONTARY_REQUEST,
        associations.get(0),
        activityAddresses.get(0),
        null,
        null
      ),
      new Activity(
        "Réhabilitation d'un refuge",
        LocalDateTime.parse("2025-12-10 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        "Rejoignez-nous pour rénover un refuge pour animaux abandonnés.",
        SECOND_ACTIVITY_VOLONTARY_REQUEST,
        associations.get(1),
        associationAddresses.get(1),
        null,
        null
      )
    );

    List<ActivityImage> activityImages = Arrays.asList(
      new ActivityImage(imagesTwo.get(0), activities.get(0)),
      new ActivityImage(imagesTwo.get(1), activities.get(0)),
      new ActivityImage(imagesTwo.get(NUMBER_TWO), activities.get(1))
    );

    List<ActivityTheme> activityThemes = Arrays.asList(
      new ActivityTheme(activities.get(0), allThemesForActivity.get(0)),
      new ActivityTheme(activities.get(0), allThemesForActivity.get(ACTIVITY_THEME_NUMBER)),
      new ActivityTheme(activities.get(1), allThemesForActivity.get(1))
    );

    List<Geolocation> activityLocalisations = List.of(new Geolocation(FIRST_ACTIVITY_LONGITUDE, FIRST_ACTIVITY_LATITUDE));

    activities.get(0).setGeolocation(activityLocalisations.get(0));
    activities.get(1).setGeolocation(associationLocalisations.get(1));

    // initiate exemples of voluntaries
    List<Image> voluntaryAvatars = List.of(new Image("/images/Voluntary/defaultAvatar.png", ImageEnumType.AVATAR));

    List<Voluntary> voluntaries = Arrays.asList(
      new Voluntary(
        new HashSet<>(List.of(UserEnumType.ROLE_VOLUNTARY)),
        AccountEnumType.ACTIVE,
        "$2a$10$jw6BeI/txUaC1BQNGYZn4.hs5wpmLhe2uYpTBB40oUveFE3ZRQYQq", // password is "Password"
        "pierre@gmail.com",
        "Nantes",
        firstCountry,
        "Pierre",
        "Lapin",
        voluntaryAvatars.get(0),
        "+33612345678",
        null,
        null
      ),
      new Voluntary(
        new HashSet<>(List.of(UserEnumType.ROLE_ADMIN, UserEnumType.ROLE_VOLUNTARY)),
        AccountEnumType.ACTIVE,
        "$2a$10$jw6BeI/txUaC1BQNGYZn4.hs5wpmLhe2uYpTBB40oUveFE3ZRQYQq", // password is "Password"
        "admin@gmail.com",
        "Nantes",
        firstCountry,
        "Jacques",
        "Loyal",
        voluntaryAvatars.get(0),
        "+33612345678",
        null,
        null
      )
    );

    List<Preferences> voluntaryPreferences = Arrays.asList(new Preferences(voluntaries.get(0)), new Preferences(voluntaries.get(1)));

    List<AssociationFollower> voluntaryAssociationFollowers = Arrays.asList(
      new AssociationFollower(true, true, voluntaries.get(0), associations.get(0))
    );

    List<ActivityVoluntary> activityVoluntaries = Arrays.asList(new ActivityVoluntary(false, true, voluntaries.get(0), activities.get(0)));

    List<Geolocation> voluntaryLocalisations = Arrays.asList(new Geolocation(FIRST_VOLUNTARY_LONGITUDE, FIRST_VOLUNTARY_LATITUDE));

    voluntaries.get(0).setGeolocation(voluntaryLocalisations.get(0));
    voluntaries.get(1).setGeolocation(voluntaryLocalisations.get(0));

    // save messages between associations and voluntaries for a specific activity
    List<Message> allMessageFirstActivity = Arrays.asList(
      new Message(
        "Bonjour à tous, merci de participer !",
        associations.get(0),
        activities.get(0),
        LocalDateTime.of(YEAR_MESSAGE, Month.MARCH, DAY_MESSAGE_ONE, DAY_MESSAGE_ONE, 0)
      ),
      new Message(
        "Bonjour, Faut-il prévoir quelque chose? Des habits de rechange ?",
        voluntaries.get(0),
        activities.get(0),
        LocalDateTime.of(YEAR_MESSAGE, Month.MARCH, DAY_MESSAGE_TWO, DAY_MESSAGE_ONE, 0)
      ),
      new Message(
        "Effectivement, il serait plus sage de prévoir quelques affaires." +
        "\n" +
        " Plutôt des affaires chaudes, il est prévu des températures négatives pour le jour de l'évènement !",
        associations.get(0),
        activities.get(0),
        LocalDateTime.of(YEAR_MESSAGE, Month.MARCH, DAY_MESSAGE_TWO, DAY_MESSAGE_TWO, 0)
      )
    );

    // initiate reports
    List<Report> allReports = Arrays.asList(
      new Report(
        StatusReportEnumType.IN_PROGRESS,
        ReasonReportEnumType.RULE_VIOLATION,
        voluntaries.get(0),
        associations.get(0),
        voluntaries.get(1),
        "Lors de ma participation à l’évenement d’aide au SDF, à plusieurs reprise le référent Mr.Patate à tenu des propos dégrant envers les femmes.",
        "Ce retour concorde avec les 3 précédents."
      )
    );

    return args -> {
      // save general data
      countryRepository.save(firstCountry);
      themeRepository.saveAll(allThemesForActivity);

      // save associations
      imageRepository.saveAll(images);
      addressRepository.saveAll(associationAddresses);
      geolocationRepository.saveAll(associationLocalisations);
      associationRepository.saveAll(associations);
      associationImageRepository.saveAll(associationImages);
      preferencesRepository.saveAll(associationPreferences);
      statisticRepository.saveAll(associationStatistics);

      // save activities
      imageRepository.saveAll(imagesTwo);
      addressRepository.saveAll(activityAddresses);
      geolocationRepository.saveAll(activityLocalisations);
      activityRepository.saveAll(activities);
      activityThemeRepository.saveAll(activityThemes);
      activityImageRepository.saveAll(activityImages);

      // save voluntaries
      imageRepository.saveAll(voluntaryAvatars);
      geolocationRepository.saveAll(voluntaryLocalisations);
      voluntaryRepository.saveAll(voluntaries);
      preferencesRepository.saveAll(voluntaryPreferences);
      associationFollowerRepository.saveAll(voluntaryAssociationFollowers);
      activityVoluntaryRepository.saveAll(activityVoluntaries);

      // save messages of activity between associations and voluntaries
      messageRepository.saveAll(allMessageFirstActivity);

      // save reports
      reportRepository.saveAll(allReports);
    };
  }
}
