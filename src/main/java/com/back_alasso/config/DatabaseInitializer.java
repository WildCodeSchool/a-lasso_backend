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
  public static final int HOUSE_NUMBER_NICE = 30;
  public static final int HOUSE_NUMBER_LILLE = 14;
  public static final int HOUSE_NUMBER_BORDEAUX = 5;
  public static final int FIRST_ASSO_YEAR_FOUNDED = 1864;
  public static final int HOUSE_NUMBER_NANCY = 8;
  public static final int FIRST_ASSO_MONTH_FOUNDED = 8;
  public static final int FIRST_ASSO_DAY_FOUNDED = 25;
  public static final int SECOND_ASSO_YEAR_FOUNDED = 1845;
  public static final int THIRD_HOUSE_NUMBER = 25;
  public static final int FOURTH_HOUSE_NUMBER = 14;
  public static final int FIFTH_HOUSE_NUMBER = 78;
  public static final int THIRD_ASSO_YEAR_FOUNDED = 1985;
  public static final int FOURTH_ASSO_YEAR_FOUNDED = 1999;
  public static final int FIFTH_ASSO_YEAR_FOUNDED = 2008;
  public static final int YEAR_MESSAGE = 2025;
  public static final int DAY_MESSAGE_ONE = 10;
  public static final int DAY_MESSAGE_TWO = 11;

  public static final int SECOND_ASSO_MONTH_FOUNDED = 12;
  public static final int SECOND_ASSO_DAY_FOUNDED = 2;
  public static final double FIRST_ASSO_LONGITUDE = -1.5043288;
  public static final double FIRST_ASSO_LATITUDE = 47.2492735;
  public static final double SECOND_ASSO_LONGITUDE = -1.418277;
  public static final double SECOND_ASSO_LATITUDE = 47.3162423;
  public static final double THIRD_ASSO_LONGITUDE = 2.3522; // Paris
  public static final double THIRD_ASSO_LATITUDE = 48.8566;
  public static final double FOURTH_ASSO_LONGITUDE = 5.3698; // Nancy
  public static final double FOURTH_ASSO_LATITUDE = 48.6921;
  public static final double FIFTH_ASSO_LONGITUDE = 4.8357; // Lyon
  public static final double FIFTH_ASSO_LATITUDE = 45.7640;
  public static final int FIRST_ASSO_FIRST_STATS = 70521;
  public static final int FIRST_ASSO_SECOND_STATS = 64430;
  public static final int FIRST_ASSO_THIRD_STATS = 10446;
  public static final int SECOND_ASSO_FIRST_STATS = 64;
  public static final int SECOND_ASSO_SECOND_STATS = 5127;
  public static final int SECOND_ASSO_THIRD_STATS = 45000;
  public static final int THIRD_ASSO_FIRST_STATS = 26;
  public static final int THIRD_ASSO_SECOND_STATS = 1500;
  public static final int THIRD_ASSO_THIRD_STATS = 80;
  public static final int FOURTH_ASSO_FIRST_STATS = 5000;
  public static final int FOURTH_ASSO_SECOND_STATS = 1800;
  public static final int FOURTH_ASSO_THIRD_STATS = 3000;
  public static final int FIFTH_ASSO_FIRST_STATS = 290;
  public static final int FIFTH_ASSO_SECOND_STATS = 3500;
  public static final int FIFTH_ASSO_THIRD_STATS = 18;

  public static final double FIRST_ACTIVITY_LATITUDE = 47.2173;
  public static final double FIRST_ACTIVITY_LONGITUDE = -1.5534;
  public static final double THIRD_ACTIVITY_LONGITUDE = 7.261953;
  public static final double THIRD_ACTIVITY_LATITUDE = 43.710173;
  public static final double FOURTH_ACTIVITY_LONGITUDE = 3.057256;
  public static final double FOURTH_ACTIVITY_LATITUDE = 50.629250;
  public static final double FIFTH_ACTIVITY_LONGITUDE = 6.184417;
  public static final double FIFTH_ACTIVITY_LATITUDE = 48.692054;
  public static final double SIXTH_ACTIVITY_LONGITUDE = -0.579180;
  public static final double SIXTH_ACTIVITY_LATITUDE = 44.837789;
  public static final double SEVENTH_ACTIVITY_LONGITUDE = 4.835659;
  public static final double SEVENTH_ACTIVITY_LATITUDE = 45.764043;
  public static final long FIRST_ACTIVITY_VOLONTARY_REQUEST = 10;
  public static final long SECOND_ACTIVITY_VOLONTARY_REQUEST = 20;
  public static final long THIRD_ACTIVITY_VOLONTARIES_REQUEST = 25L;
  public static final long FOURTH_ACTIVITY_VOLONTARIES_REQUEST = 15L;
  public static final long FIFTH_ACTIVITY_VOLONTARIES_REQUEST = 10L;
  public static final long SIXTH_ACTIVITY_VOLONTARIES_REQUEST = 30L;
  public static final long SEVENTH_ACTIVITY_VOLONTARIES_REQUEST = 40L;
  public static final int ACTIVITY_THEME_NUMBER = 5;

  public static final double FIRST_VOLUNTARY_LATITUDE = 47.218637;
  public static final double FIRST_VOLUNTARY_LONGITUDE = -1.554136;
  public static final int NUMBER_ZERO = 0;
  public static final int NUMBER_ONE = 1;
  public static final int NUMBER_TWO = 2;
  public static final int NUMBER_THREE = 3;
  public static final int NUMBER_FOUR = 4;
  public static final int NUMBER_FIVE = 5;
  public static final int NUMBER_SIX = 6;
  public static final int NUMBER_SEVEN = 7;
  public static final int NUMBER_EIGHT = 8;
  public static final int NUMBER_NINE = 9;
  public static final int NUMBER_TEN = 10;
  public static final int NUMBER_ELEVEN = 11;
  public static final int NUMBER_TWELVE = 12;
  public static final int NUMBER_EIGHTEEN = 18;
  public static final int NUMBER_TWENTY = 20;
  public static final int NUMBER_FOURTEEN = 14;

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
      new Address(SECOND_HOUSE_NUMBER, "chemin de Gralan", null, "44470", "CARQUEFOU", firstCountry),
      new Address(THIRD_HOUSE_NUMBER, "avenue des Champs-Élysées", null, "75008", "PARIS", firstCountry),
      new Address(FOURTH_HOUSE_NUMBER, "rue Saint-Jean", null, "54000", "NANCY", firstCountry),
      new Address(FIFTH_HOUSE_NUMBER, "rue de la République", null, "69002", "LYON", firstCountry)
    );

    List<Image> images = Arrays.asList(
      new Image("/images/Association/croixRouge.png", ImageEnumType.PROFILE_ASSOCIATION),
      new Image("/images/Association/logoCroixRouge.png", ImageEnumType.LOGO),
      new Image("/images/Association/logoSPA.png", ImageEnumType.LOGO),
      new Image("/images/Association/spaProfile.jpg", ImageEnumType.PROFILE_ASSOCIATION),
      new Image("/images/Association/logoUrbanNature.png", ImageEnumType.LOGO),
      new Image("/images/Association/plage_1.jpg", ImageEnumType.PROFILE_ASSOCIATION),
      new Image("/images/Association/logoJeunesseSolidaire.png", ImageEnumType.LOGO),
      new Image("/images/Association/ecriture_3.jpg", ImageEnumType.PROFILE_ASSOCIATION),
      new Image("/images/Association/logoCulturArt.png", ImageEnumType.LOGO),
      new Image("/images/Association/spectacle_2.jpg", ImageEnumType.PROFILE_ASSOCIATION)
    );

    List<Association> associations = Arrays.asList(
      new Association(
        "La Croix-Rouge française agit pour protéger et relever sans condition, les personnes en situation de vulnérabilité et construire avec elles leur résilience.",
        "Henry DUNANT",
        LocalDate.of(FIRST_ASSO_YEAR_FOUNDED, FIRST_ASSO_MONTH_FOUNDED, FIRST_ASSO_DAY_FOUNDED),
        "LA CROIX ROUGE",
        associationAddresses.get(NUMBER_ZERO),
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
        associationAddresses.get(NUMBER_ONE),
        null,
        new HashSet<>(List.of(UserEnumType.ROLE_ASSOCIATION)),
        AccountEnumType.ACTIVE,
        "$2a$10$jw6BeI/txUaC1BQNGYZn4.hs5wpmLhe2uYpTBB40oUveFE3ZRQYQq", // password is "Password"
        "spa@gmail.com",
        null,
        "https://www.la-spa.fr/"
      ),
      new Association(
        "Association dédiée à la protection de l'environnement en milieu urbain.",
        "Alice VERT",
        LocalDate.of(THIRD_ASSO_YEAR_FOUNDED, Month.MARCH, NUMBER_FOURTEEN),
        "URBAN NATURE",
        associationAddresses.get(NUMBER_TWO),
        null,
        new HashSet<>(List.of(UserEnumType.ROLE_ASSOCIATION)),
        AccountEnumType.ACTIVE,
        "$2a$10$jw6BeI/txUaC1BQNGYZn4.hs5wpmLhe2uYpTBB40oUveFE3ZRQYQq",
        "urban.nature@gmail.com",
        null,
        "https://www.facebook.com/TracerEnvironnement/?locale=fr_FR"
      ),
      new Association(
        "Aide sociale et éducative pour les jeunes en difficulté.",
        "Bertrand JEUNE",
        LocalDate.of(FOURTH_ASSO_YEAR_FOUNDED, Month.SEPTEMBER, NUMBER_TWENTY),
        "JEUNES SOLIDAIRES",
        associationAddresses.get(NUMBER_THREE),
        null,
        new HashSet<>(List.of(UserEnumType.ROLE_ASSOCIATION)),
        AccountEnumType.ACTIVE,
        "$2a$10$jw6BeI/txUaC1BQNGYZn4.hs5wpmLhe2uYpTBB40oUveFE3ZRQYQq",
        "jeunes.solidaires@gmail.com",
        null,
        "https://lesjeunessolidaires.org"
      ),
      new Association(
        "Promotion culturelle à travers des ateliers et expositions artistiques.",
        "Chloé ARTS",
        LocalDate.of(FIFTH_ASSO_YEAR_FOUNDED, Month.JUNE, NUMBER_EIGHTEEN),
        "CULTUR'ART",
        associationAddresses.get(NUMBER_FOUR),
        null,
        new HashSet<>(List.of(UserEnumType.ROLE_ASSOCIATION)),
        AccountEnumType.ACTIVE,
        "$2a$10$jw6BeI/txUaC1BQNGYZn4.hs5wpmLhe2uYpTBB40oUveFE3ZRQYQq",
        "cultur.art@gmail.com",
        null,
        "https://www.cultur.art/"
      )
    );

    List<Preferences> associationPreferences = Arrays.asList(
      new Preferences(associations.get(NUMBER_ZERO)),
      new Preferences(associations.get(NUMBER_ONE))
    );

    List<Geolocation> associationLocalisations = Arrays.asList(
      new Geolocation(FIRST_ASSO_LONGITUDE, FIRST_ASSO_LATITUDE),
      new Geolocation(SECOND_ASSO_LONGITUDE, SECOND_ASSO_LATITUDE),
      new Geolocation(THIRD_ASSO_LONGITUDE, THIRD_ASSO_LATITUDE),
      new Geolocation(FOURTH_ASSO_LONGITUDE, FOURTH_ASSO_LATITUDE),
      new Geolocation(FIFTH_ASSO_LONGITUDE, FIFTH_ASSO_LATITUDE)
    );

    associations.get(NUMBER_ZERO).setGeolocation(associationLocalisations.get(NUMBER_ZERO));
    associations.get(NUMBER_ONE).setGeolocation(associationLocalisations.get(NUMBER_ONE));
    associations.get(NUMBER_TWO).setGeolocation(associationLocalisations.get(NUMBER_TWO));
    associations.get(NUMBER_THREE).setGeolocation(associationLocalisations.get(NUMBER_THREE));
    associations.get(NUMBER_FOUR).setGeolocation(associationLocalisations.get(NUMBER_FOUR));

    List<AssociationImage> associationImages = Arrays.asList(
      new AssociationImage(images.get(NUMBER_ZERO), associations.get(NUMBER_ZERO)),
      new AssociationImage(images.get(NUMBER_ONE), associations.get(NUMBER_ZERO)),
      new AssociationImage(images.get(NUMBER_TWO), associations.get(NUMBER_ONE)),
      new AssociationImage(images.get(NUMBER_THREE), associations.get(NUMBER_ONE)),
      new AssociationImage(images.get(NUMBER_FOUR), associations.get(NUMBER_TWO)),
      new AssociationImage(images.get(NUMBER_FIVE), associations.get(NUMBER_TWO)),
      new AssociationImage(images.get(NUMBER_SIX), associations.get(NUMBER_THREE)),
      new AssociationImage(images.get(NUMBER_SEVEN), associations.get(NUMBER_THREE)),
      new AssociationImage(images.get(NUMBER_EIGHT), associations.get(NUMBER_FOUR)),
      new AssociationImage(images.get(NUMBER_NINE), associations.get(NUMBER_FOUR))
    );

    List<Statistic> associationStatistics = Arrays.asList(
      new Statistic(FIRST_ASSO_FIRST_STATS, "bénévoles", associations.get(NUMBER_ZERO)),
      new Statistic(FIRST_ASSO_SECOND_STATS, "personnes prises en charge", associations.get(NUMBER_ZERO)),
      new Statistic(FIRST_ASSO_THIRD_STATS, "interventions", associations.get(NUMBER_ZERO)),
      new Statistic(SECOND_ASSO_FIRST_STATS, "refuges", associations.get(NUMBER_ONE)),
      new Statistic(SECOND_ASSO_SECOND_STATS, "bénévoles", associations.get(NUMBER_ONE)),
      new Statistic(SECOND_ASSO_THIRD_STATS, "animaux pris en charge", associations.get(NUMBER_ONE)),
      new Statistic(THIRD_ASSO_FIRST_STATS, "jardins réhabilités", associations.get(NUMBER_TWO)),
      new Statistic(THIRD_ASSO_SECOND_STATS, "volontaires", associations.get(NUMBER_TWO)),
      new Statistic(THIRD_ASSO_THIRD_STATS, "communes partenaires", associations.get(NUMBER_TWO)),
      new Statistic(FOURTH_ASSO_FIRST_STATS, "jeunes aidés", associations.get(NUMBER_THREE)),
      new Statistic(FOURTH_ASSO_SECOND_STATS, "interventions", associations.get(NUMBER_THREE)),
      new Statistic(FOURTH_ASSO_THIRD_STATS, "bénévoles", associations.get(NUMBER_THREE)),
      new Statistic(FIFTH_ASSO_FIRST_STATS, "oeuvres d'arts", associations.get(NUMBER_FOUR)),
      new Statistic(FIFTH_ASSO_SECOND_STATS, "artistes rencontrés", associations.get(NUMBER_FOUR)),
      new Statistic(FIFTH_ASSO_THIRD_STATS, "établissements", associations.get(NUMBER_FOUR))
    );

    // initiate examples of activities
    List<Image> imagesTwo = Arrays.asList(
      new Image("/images/Activity/maraude1.png", ImageEnumType.ACTIVITY),
      new Image("/images/Activity/maraude2.jpg", ImageEnumType.ACTIVITY),
      new Image("/images/Activity/spaActivity.jpg", ImageEnumType.ACTIVITY),
      new Image("/images/Activity/jardin_2.jpg", ImageEnumType.ACTIVITY),
      new Image("/images/Activity/jardin_3.jpg", ImageEnumType.ACTIVITY),
      new Image("/images/Activity/ecriture_1.jpg", ImageEnumType.ACTIVITY),
      new Image("/images/Activity/ecriture_2.jpg", ImageEnumType.ACTIVITY),
      new Image("/images/Activity/plage_2.jpg", ImageEnumType.ACTIVITY),
      new Image("/images/Activity/spectacle_1.jpg", ImageEnumType.ACTIVITY),
      new Image("/images/Activity/spectacle_3.jpg", ImageEnumType.ACTIVITY),
      new Image("/images/Activity/cuisine_1.jpg", ImageEnumType.ACTIVITY),
      new Image("/images/Activity/cuisine_2.jpg", ImageEnumType.ACTIVITY),
      new Image("/images/Activity/cuisine_3.jpg", ImageEnumType.ACTIVITY)
    );

    List<Address> activityAddresses = Arrays.asList(
      new Address(FIRST_HOUSE_NUMBER, "rue du bonheur", null, "44300", "NANTES", firstCountry), // maraude
      new Address(SECOND_HOUSE_NUMBER, "chemin de Gralan", null, "44470", "CARQUEFOU", firstCountry), // refuge SPA
      new Address(HOUSE_NUMBER_NICE, "Promenade des Anglais", null, "06000", "NICE", firstCountry), // plage Nice
      new Address(HOUSE_NUMBER_LILLE, "Rue Nationale", null, "59000", "LILLE", firstCountry), // cuisine solidaire Lille
      new Address(HOUSE_NUMBER_NANCY, "Place Stanislas", null, "54000", "NANCY", firstCountry), // atelier écriture Nancy
      new Address(HOUSE_NUMBER_BORDEAUX, "Rue Sainte-Catherine", null, "33000", "BORDEAUX", firstCountry), // jardins urbains Bordeaux
      new Address(FIRST_HOUSE_NUMBER, "Rue Victor Hugo", null, "69002", "LYON", firstCountry) // spectacle Lyon
    );

    List<Activity> activities = Arrays.asList(
      new Activity(
        "La maraude",
        LocalDateTime.parse("2025-12-22 04:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        "« La maraude » est une action de solidarité humaine essentielle qui consiste à aller à la rencontre des personnes sans-abri dans les rues de Nantes. En participant à cette maraude, vous jouerez un rôle actif dans la création de lien social, en offrant un moment d’écoute bienveillante, un sourire, des boissons chaudes et parfois des kits d’hygiène ou des couvertures. L’objectif n’est pas seulement d’apporter une aide matérielle, mais aussi de redonner un peu de dignité et de chaleur humaine à ceux qui sont trop souvent invisibilisés. Cette activité est organisée par des bénévoles expérimentés, dans le respect et la discrétion.",
        FIRST_ACTIVITY_VOLONTARY_REQUEST,
        associations.get(NUMBER_ZERO),
        activityAddresses.get(NUMBER_ZERO),
        null,
        null
      ),
      new Activity(
        "Réhabilitation refuge",
        LocalDateTime.parse("2025-12-10 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        "Rejoignez une équipe de bénévoles passionnés pour redonner vie à un refuge pour animaux abandonnés à Carquefou. L’objectif de cette activité est de remettre en état les infrastructures du refuge : repeindre les murs, réparer les enclos, installer des abris confortables, nettoyer les espaces communs, et créer un environnement sûr et accueillant pour chiens, chats et autres compagnons à quatre pattes. Ce chantier solidaire contribue non seulement au bien-être animal, mais renforce aussi la capacité d’accueil du refuge. Une belle manière de lier engagement écologique et cause animale.",
        SECOND_ACTIVITY_VOLONTARY_REQUEST,
        associations.get(NUMBER_ONE),
        activityAddresses.get(NUMBER_ONE),
        null,
        null
      ),
      new Activity(
        "Nettoyage de plage",
        LocalDateTime.parse("2025-07-15 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        "Chaque été, les plages de Nice subissent l’impact de la pollution plastique et des déchets laissés par les vacanciers. Cette opération de nettoyage écologique vise à collecter les déchets tout en sensibilisant les promeneurs à la protection de notre littoral. Munis de gants et de sacs, les bénévoles sillonnent le sable, les rochers et les zones naturelles pour ramasser plastiques, mégots, emballages et objets flottants. L’activité est accompagnée d’un temps d’échange sur les enjeux environnementaux, la biodiversité marine et les gestes écoresponsables à adopter au quotidien.",
        THIRD_ACTIVITY_VOLONTARIES_REQUEST,
        associations.get(NUMBER_TWO),
        activityAddresses.get(NUMBER_TWO),
        null,
        null
      ),
      new Activity(
        "Atelier cuisine",
        LocalDateTime.parse("2025-08-20 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        "Cet atelier culinaire à Lille est bien plus qu’une simple session de cuisine. Il s’agit d’un moment de convivialité et d’entraide où bénévoles et bénéficiaires se retrouvent pour préparer ensemble un repas chaud destiné aux personnes sans domicile fixe ou en grande précarité. Vous participerez à toutes les étapes : choix des recettes, préparation des ingrédients, cuisson, dressage, puis distribution des repas. L’atelier favorise les rencontres, le partage des savoir-faire, et contribue à redonner le sourire à ceux qui vivent dans l’isolement. Aucune compétence culinaire n’est requise, seulement de la bonne volonté.",
        FOURTH_ACTIVITY_VOLONTARIES_REQUEST,
        associations.get(NUMBER_ZERO),
        activityAddresses.get(NUMBER_THREE),
        null,
        null
      ),
      new Activity(
        "Atelier d'écriture",
        LocalDateTime.parse("2025-09-12 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        "Destiné à un public de jeunes en difficulté à Nancy, cet atelier d’écriture créative vise à développer l’expression de soi, la confiance et la créativité par le biais de l’écriture. Encadré par des animateurs passionnés, l’atelier propose des jeux littéraires, des récits personnels, des slam, des poèmes ou des histoires imaginaires. Les participants découvrent la puissance des mots pour se raconter, libérer leurs émotions et échanger avec les autres. Une restitution finale peut être organisée sous forme de lecture publique ou de publication. Une expérience artistique et humaine très enrichissante.",
        FIFTH_ACTIVITY_VOLONTARIES_REQUEST,
        associations.get(NUMBER_THREE),
        activityAddresses.get(NUMBER_FOUR),
        null,
        null
      ),
      new Activity(
        "Réhabilitation jardins",
        LocalDateTime.parse("2025-10-05 08:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        "Au cœur de Bordeaux, cette activité de réhabilitation de jardins urbains transforme des friches abandonnées en véritables oasis de verdure. Accompagné d’experts en permaculture et d’habitants du quartier, vous participerez à la remise en état de potagers partagés, à la plantation d’arbres et de fleurs locales, à la mise en place de composteurs et à la création de mobiliers urbains en matériaux recyclés. L’objectif est de favoriser la biodiversité, de lutter contre les îlots de chaleur et de recréer du lien entre les habitants autour d’un projet durable, collectif et écologique.",
        SIXTH_ACTIVITY_VOLONTARIES_REQUEST,
        associations.get(NUMBER_TWO),
        activityAddresses.get(NUMBER_FIVE),
        null,
        null
      ),
      new Activity(
        "Spectacle solidaire",
        LocalDateTime.parse("2025-11-20 19:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        "Participez à l’organisation d’un spectacle solidaire à Lyon mêlant musique, théâtre, danse et humour, avec des artistes bénévoles engagés. Cet événement a pour but de récolter des fonds pour financer des actions sociales locales, mais aussi de proposer une soirée culturelle gratuite et ouverte à tous, dans un esprit de partage et d’inclusion. Vous pouvez aider à la logistique, à l’accueil du public, à la communication, ou encore à la technique. Le spectacle est suivi d’un moment d’échange autour d’un buffet solidaire. Une expérience festive, engagée, et riche en émotions.",
        SEVENTH_ACTIVITY_VOLONTARIES_REQUEST,
        associations.get(NUMBER_FOUR),
        activityAddresses.get(NUMBER_SIX),
        null,
        null
      )
    );

    List<ActivityImage> activityImages = Arrays.asList(
      new ActivityImage(imagesTwo.get(NUMBER_ZERO), activities.get(NUMBER_ZERO)),
      new ActivityImage(imagesTwo.get(NUMBER_ONE), activities.get(NUMBER_ZERO)),
      new ActivityImage(imagesTwo.get(NUMBER_TWO), activities.get(NUMBER_ONE)),
      new ActivityImage(imagesTwo.get(DatabaseInitializer.NUMBER_THREE), activities.get(NUMBER_FIVE)),
      new ActivityImage(imagesTwo.get(NUMBER_FOUR), activities.get(NUMBER_FIVE)),
      new ActivityImage(imagesTwo.get(NUMBER_FIVE), activities.get(NUMBER_FOUR)),
      new ActivityImage(imagesTwo.get(NUMBER_SIX), activities.get(NUMBER_FOUR)),
      new ActivityImage(imagesTwo.get(NUMBER_SEVEN), activities.get(NUMBER_TWO)),
      new ActivityImage(imagesTwo.get(NUMBER_EIGHT), activities.get(NUMBER_SIX)),
      new ActivityImage(imagesTwo.get(NUMBER_NINE), activities.get(NUMBER_SIX)),
      new ActivityImage(imagesTwo.get(NUMBER_TEN), activities.get(NUMBER_THREE)),
      new ActivityImage(imagesTwo.get(NUMBER_ELEVEN), activities.get(NUMBER_THREE)),
      new ActivityImage(imagesTwo.get(NUMBER_TWELVE), activities.get(NUMBER_THREE))
    );

    List<ActivityTheme> activityThemes = Arrays.asList(
      new ActivityTheme(activities.get(NUMBER_ZERO), allThemesForActivity.get(NUMBER_ZERO)),
      new ActivityTheme(activities.get(NUMBER_ZERO), allThemesForActivity.get(ACTIVITY_THEME_NUMBER)),
      new ActivityTheme(activities.get(NUMBER_ONE), allThemesForActivity.get(NUMBER_ONE))
    );

    List<Geolocation> activityLocalisations = Arrays.asList(
      new Geolocation(FIRST_ACTIVITY_LONGITUDE, FIRST_ACTIVITY_LATITUDE), // Nantes
      new Geolocation(SECOND_ASSO_LONGITUDE, SECOND_ASSO_LATITUDE), // Carquefou
      new Geolocation(THIRD_ACTIVITY_LONGITUDE, THIRD_ACTIVITY_LATITUDE), // Nice
      new Geolocation(FOURTH_ACTIVITY_LONGITUDE, FOURTH_ACTIVITY_LATITUDE), // Lille
      new Geolocation(FIFTH_ACTIVITY_LONGITUDE, FIFTH_ACTIVITY_LATITUDE), // Nancy
      new Geolocation(SIXTH_ACTIVITY_LONGITUDE, SIXTH_ACTIVITY_LATITUDE), // Bordeaux
      new Geolocation(SEVENTH_ACTIVITY_LONGITUDE, SEVENTH_ACTIVITY_LATITUDE) // Lyon
    );

    for (int i = 0; i < activities.size(); i++) {
      activities.get(i).setGeolocation(activityLocalisations.get(i));
    }

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
        voluntaryAvatars.get(NUMBER_ZERO),
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
        voluntaryAvatars.get(NUMBER_ZERO),
        "+33612345678",
        null,
        null
      )
    );

    List<Preferences> voluntaryPreferences = Arrays.asList(
      new Preferences(voluntaries.get(NUMBER_ZERO)),
      new Preferences(voluntaries.get(NUMBER_ONE))
    );

    List<AssociationFollower> voluntaryAssociationFollowers = Arrays.asList(
      new AssociationFollower(true, true, voluntaries.get(NUMBER_ZERO), associations.get(NUMBER_ZERO))
    );

    List<ActivityVoluntary> activityVoluntaries = Arrays.asList(
      new ActivityVoluntary(false, true, voluntaries.get(NUMBER_ZERO), activities.get(NUMBER_ZERO))
    );

    List<Geolocation> voluntaryLocalisations = Arrays.asList(new Geolocation(FIRST_VOLUNTARY_LONGITUDE, FIRST_VOLUNTARY_LATITUDE));

    voluntaries.get(NUMBER_ZERO).setGeolocation(voluntaryLocalisations.get(NUMBER_ZERO));
    voluntaries.get(NUMBER_ONE).setGeolocation(voluntaryLocalisations.get(NUMBER_ZERO));

    // save messages between associations and voluntaries for a specific activity
    List<Message> allMessageFirstActivity = Arrays.asList(
      new Message(
        "Bonjour à tous, merci de participer !",
        associations.get(NUMBER_ZERO),
        activities.get(NUMBER_ZERO),
        LocalDateTime.of(YEAR_MESSAGE, Month.MARCH, DAY_MESSAGE_ONE, DAY_MESSAGE_ONE, NUMBER_ZERO)
      ),
      new Message(
        "Bonjour, Faut-il prévoir quelque chose? Des habits de rechange ?",
        voluntaries.get(NUMBER_ZERO),
        activities.get(NUMBER_ZERO),
        LocalDateTime.of(YEAR_MESSAGE, Month.MARCH, DAY_MESSAGE_TWO, DAY_MESSAGE_ONE, NUMBER_ZERO)
      ),
      new Message(
        "Effectivement, il serait plus sage de prévoir quelques affaires." +
        "\n" +
        " Plutôt des affaires chaudes, il est prévu des températures négatives pour le jour de l'évènement !",
        associations.get(NUMBER_ZERO),
        activities.get(NUMBER_ZERO),
        LocalDateTime.of(YEAR_MESSAGE, Month.MARCH, DAY_MESSAGE_TWO, DAY_MESSAGE_TWO, NUMBER_ZERO)
      )
    );

    // initiate reports
    List<Report> allReports = Arrays.asList(
      new Report(
        StatusReportEnumType.IN_PROGRESS,
        ReasonReportEnumType.RULE_VIOLATION,
        voluntaries.get(NUMBER_ZERO),
        associations.get(NUMBER_ZERO),
        voluntaries.get(NUMBER_ONE),
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
