//package com.back_alasso.config;
//
//import com.back_alasso.Activity.Activity;
//import com.back_alasso.Activity.ActivityRepository;
//import com.back_alasso.ActivityImage.ActivityImage;
//import com.back_alasso.ActivityVoluntary.ActivityVoluntary;
//import com.back_alasso.ActivityTheme.ActivityTheme;
//import com.back_alasso.Association.Association;
//import com.back_alasso.Address.Address;
//import com.back_alasso.Image.Image;
//import com.back_alasso.Image.ImageEnumType;
//import com.back_alasso.Theme.ThemeNameEnumType;
//import com.back_alasso.Voluntary.Voluntary;
//import com.back_alasso.Country.Country;
//import com.back_alasso.Theme.Theme;
//import com.back_alasso.AssociationImage.AssociationImage;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//@Configuration
//public class DatabaseInitializer {
//
//    private final ActivityRepository activityRepository;
//
//    public DatabaseInitializer(ActivityRepository activityRepository) {
//        this.activityRepository = activityRepository;
//    }
//
//    @Bean
//    CommandLineRunner init() {
//        return args -> {
//            List<Activity> activities = new ArrayList<>();
//            for (int i = 0; i < 5; i++) {
//                Activity activity = generateRandomActivity();
//                activities.add(activity);
//            }
//            activityRepository.saveAll(activities);
//        };
//    }
//
//    private Activity generateRandomActivity() {
//        Activity activity = new Activity();
//
//        // Titre aléatoire
//        activity.setTitle(generateRandomString(10, 50));
//
//        // Description aléatoire
//        activity.setDescription(generateRandomString(30, 100));
//
//        // Date aléatoire
//        activity.setDate(generateRandomDate());
//
//        // Nombre de volontaires demandés
//        activity.setVolontaries_request(new Random().nextLong(100, 500));
//
//        // Associer à une association fictive
//        Association association = new Association();
//        association.setName(generateRandomString(5, 20));
//        activity.setAssociation(association);
//
//        // Associer à une adresse fictive
//        Address address = new Address();
//        address.setCity(generateRandomString(5, 20));
//        address.setStreet_name(generateRandomString(5, 20));
//        address.setZipCode("ZIP" + new Random().nextInt(10000, 99999));
//        activity.setAddress(address);
//
//
//        // Créer des images fictives
//        List<ActivityImage> activityImages = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            ActivityImage activityImage = new ActivityImage();
//
//            Image image = new Image();
//            image.setUrl("http://example.com/image" + i + ".jpg");
//            image.setType(generateRandomImageType());
//
//            activityImage.setImage(image);
//            activityImage.setActivity(activity);
//
//            activityImages.add(activityImage);
//        }
//        activity.setActivityImages(activityImages);
//
//        // Créer des thèmes fictifs
//        List<ActivityTheme> activityThemes = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            ActivityTheme activityTheme = new ActivityTheme();
//            Theme theme = new Theme();
//            theme.setName(generateRandomThemeType());
//            theme.setIcon_url("http://example.com/icon" + i + ".png");
//
//            activityTheme.setTheme(theme);
//            activityTheme.setActivity(activity);
//            activityThemes.add(activityTheme);
//        }
//        activity.setActivityThemes(activityThemes);
//
//        // Créer des volontaires fictifs
//        List<ActivityVoluntary> activityVoluntaries = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            ActivityVoluntary activityVoluntary = new ActivityVoluntary();
//            activityVoluntary.setIs_saved(new Random().nextBoolean());
//            activityVoluntary.setIs_registered(new Random().nextBoolean());
//
//            Voluntary voluntary = new Voluntary();
//            voluntary.setFirst_name(generateRandomString(3, 15));
//            voluntary.setLast_name(generateRandomString(3, 15));
//            voluntary.setCity(generateRandomString(5, 20));
//
//            activityVoluntary.setVoluntary(voluntary);
//            activityVoluntary.setActivity(activity);
//
//            activityVoluntaries.add(activityVoluntary);
//        }
//        activity.setActivityVoluntaries(activityVoluntaries);
//
//        return activity;
//    }
//
//    // Méthode pour générer une chaîne aléatoire entre deux longueurs
//    private static String generateRandomString(int minLength, int maxLength) {
//        int length = new Random().nextInt(maxLength - minLength) + minLength;
//        StringBuilder stringBuilder = new StringBuilder();
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";
//        for (int i = 0; i < length; i++) {
//            stringBuilder.append(characters.charAt(new Random().nextInt(characters.length())));
//        }
//        return stringBuilder.toString();
//    }
//
//    // Méthode pour générer une date aléatoire
//    private static java.util.Date generateRandomDate() {
//        long minDate = 1672531200000L; // 01-01-2023 (en millisecondes)
//        long maxDate = 1735680000000L; // 01-01-2025 (en millisecondes)
//        return new java.util.Date(new Random().nextLong(minDate, maxDate));
//    }
//
//    // Méthode pour générer un UUID
//    private static String generateRandomUUID() {
//        return java.util.UUID.randomUUID().toString();
//    }
//
//    // Méthode pour générer un type d'image aléatoire
//    private static ImageEnumType generateRandomImageType() {
//        return ImageEnumType.values()[new Random().nextInt(ImageEnumType.values().length)];
//    }
//
//    // Méthode pour générer un type de theme aléatoire
//    private static ThemeNameEnumType generateRandomThemeType() {
//        return ThemeNameEnumType.values()[new Random().nextInt(ImageEnumType.values().length)];
//    }
//}
