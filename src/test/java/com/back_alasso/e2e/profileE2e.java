package com.back_alasso.e2e;

import com.back_alasso.AbstractTests;
import com.back_alasso.features.Authentication.DTO.LoginVoluntaryResult;
import com.back_alasso.features.Image.Image;
import com.back_alasso.features.Image.ImageEnumType;
import com.back_alasso.features.Image.ImageRepository;
import com.back_alasso.features.Voluntary.DTO.VoluntaryLoginResponseDTO;
import com.back_alasso.features.Country.Country;
import com.back_alasso.features.Country.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class profileE2e extends AbstractTests {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @BeforeEach
    void setupDatabase() {
        Country firstCountry = new Country("France");
        countryRepository.save(firstCountry);
        Image voluntaryAvatar = new Image("/images/Voluntary/defaultAvatar.png", ImageEnumType.AVATAR);
        imageRepository.save(voluntaryAvatar);
    }

    @Test
    void shouldRegisterUserAndRetrieveItAndUpdateIt() {
        String email = "piloutre@test.com";
        String password = "Password1*";

        // 1. Register user
        String registerBody = """
                {
                  "email": "%s",
                  "password": "%s",
                  "first_name": "Piloutre",
                  "last_name": "Test",
                  "city": "Nantes",
                  "country": "France",
                  "birth_date": "1990-01-01"
                }
                """.formatted(email, password);

        String loginBody = """
                {
                  "email": "%s",
                  "password": "%s"
                }
                """.formatted(email, password);

        // Inscription
        given()
                .contentType("application/json")
                .body(registerBody)
                .when()
                .post("/auth/register/voluntary")
                .then()
                .statusCode(201);

        // Connexion et récupération du token
        LoginVoluntaryResult userResponseDTO = getLoginUserResponseDTO(loginBody);

        String token = userResponseDTO.getToken();
        VoluntaryLoginResponseDTO voluntary = userResponseDTO.getUser();

        assertThat(voluntary.email()).isEqualTo(email);
        assertThat(voluntary.first_name()).isEqualTo("Piloutre");
        assertThat(voluntary.mobile_phone()).isEqualTo(null);


        // Modification des informations de profil pour ajout téléphone et changement prénom
        String modifyProfileBody = """
                {
                  "email": "%s",
                  "password": "%s",
                  "first_name": "Super Piloutre",
                  "last_name": "Test",
                  "city": "Nantes",
                  "country": "France",
                  "birth_date": "1990-01-01",
                  "mobile_phone": "0623456789"
                }
                """.formatted(email, password);

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(modifyProfileBody)
                .when()
                .put("/voluntary/me")
                .then()
                .statusCode(200);

        // Vérification des informations modifiées
        LoginVoluntaryResult voluntaryModifiedResponseDTO = getLoginUserResponseDTO(loginBody);
        VoluntaryLoginResponseDTO modifiedVoluntary = voluntaryModifiedResponseDTO.getUser();

        assertThat(modifiedVoluntary.first_name()).isEqualTo("Super Piloutre");
        assertThat(modifiedVoluntary.mobile_phone()).isEqualTo("0623456789");
    }

    private static LoginVoluntaryResult getLoginUserResponseDTO(String loginBody) {
        return
                given()
                        .contentType("application/json")
                        .body(loginBody)
                        .when()
                        .post("/auth/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(LoginVoluntaryResult.class);
    }
}