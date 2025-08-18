package com.back_alasso.features.Geolocation;

import com.back_alasso.exception.GeolocationNotFoundException;
import com.back_alasso.features.Activity.DTO.ActivitySaveRequestDTO;
import com.back_alasso.features.Geolocation.DTO.GeolocApiGouvResponseDTO;
import com.back_alasso.features.Geolocation.DTO.GeolocationRequestDTO;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class GeolocationService {

  public static final int INT = 2;

  private static final double LATITUDE_MIN = -90.0;
  private static final double LATITUDE_MAX = 90.0;
  private static final double LONGITUDE_MIN = -180.0;
  private static final double LONGITUDE_MAX = 180.0;

  private static final double PARIS_LONGITUDE = 2.3522;
  private static final double PARIS_LATITUDE = 48.8566;
  private static final double LYON_LONGITUDE = 4.8357;
  private static final double LYON_LATITUDE = 45.7640;
  private static final double MARSEILLE_LONGITUDE = 5.3698;
  private static final double MARSEILLE_LATITUDE = 43.2965;
  private static final double TOULOUSE_LONGITUDE = 1.4442;
  private static final double TOULOUSE_LATITUDE = 43.6047;
  private static final double NICE_LONGITUDE = 7.2620;
  private static final double NICE_LATITUDE = 43.7102;
  private static final double NANTES_LONGITUDE = -1.5534;
  private static final double NANTES_LATITUDE = 47.2184;
  private static final double STRASBOURG_LONGITUDE = 7.7521;
  private static final double STRASBOURG_LATITUDE = 48.5734;
  private static final double MONTPELLIER_LONGITUDE = 3.8767;
  private static final double MONTPELLIER_LATITUDE = 43.6108;
  private static final double BORDEAUX_LONGITUDE = -0.5792;
  private static final double BORDEAUX_LATITUDE = 44.8378;
  private static final double LILLE_LONGITUDE = 3.0573;
  private static final double LILLE_LATITUDE = 50.6292;
  private static final double RENNES_LONGITUDE = -1.6778;
  private static final double RENNES_LATITUDE = 48.1173;
  private static final double REIMS_LONGITUDE = 4.0317;
  private static final double REIMS_LATITUDE = 49.2583;
  private static final double SAINT_ETIENNE_LONGITUDE = 4.3872;
  private static final double SAINT_ETIENNE_LATITUDE = 45.4397;
  private static final double TOULON_LONGITUDE = 5.9280;
  private static final double TOULON_LATITUDE = 43.1242;
  private static final double GRENOBLE_LONGITUDE = 5.7243;
  private static final double GRENOBLE_LATITUDE = 45.1885;
  private static final double ANGERS_LONGITUDE = -0.5592;
  private static final double ANGERS_LATITUDE = 47.4784;
  private static final double DIJON_LONGITUDE = 5.0415;
  private static final double DIJON_LATITUDE = 47.3220;
  private static final double BREST_LONGITUDE = -4.4860;
  private static final double BREST_LATITUDE = 48.3904;
  private static final double CLERMONT_FERRAND_LONGITUDE = 3.0863;
  private static final double CLERMONT_FERRAND_LATITUDE = 45.7797;
  private static final double TOURS_LONGITUDE = 0.6848;
  private static final double TOURS_LATITUDE = 47.3941;
  private static final double AMIENS_LONGITUDE = 2.2958;
  private static final double AMIENS_LATITUDE = 49.8941;
  private static final double LIMOGES_LONGITUDE = 1.2578;
  private static final double LIMOGES_LATITUDE = 45.8336;
  private static final double ANNECY_LONGITUDE = 6.1294;
  private static final double ANNECY_LATITUDE = 45.8992;
  private static final double PERPIGNAN_LONGITUDE = 2.8965;
  private static final double PERPIGNAN_LATITUDE = 42.6886;
  private static final double BESANCON_LONGITUDE = 6.0244;
  private static final double BESANCON_LATITUDE = 47.2378;

  private final String GEOLOC_BASE_URL_GOUV = "https://api-adresse.data.gouv.fr/search/?q=";
  private final RestTemplate restTemplate;
  private final GeolocationRepository geolocationRepository;
  private final Map<String, GeolocationRequestDTO> cityCoordinatesCache;

  public GeolocationService(RestTemplate restTemplate, GeolocationRepository geolocationRepository) {
    this.restTemplate = restTemplate;
    this.geolocationRepository = geolocationRepository;
    this.cityCoordinatesCache = initializeCityCoordinates();
  }

  public GeolocationRequestDTO getVoluntaryCoordinates(String city, String country) {
    if (city == null || city.trim().isEmpty()) {
      throw new GeolocationNotFoundException("La ville ne peut pas être vide");
    }

    try {
      return performGeocoding(city.trim());
    } catch (GeolocationNotFoundException ignored) {}

    if (country != null && !country.trim().isEmpty()) {
      try {
        String fullQuery = String.format("%s, %s", city.trim(), country.trim());
        return performGeocoding(fullQuery);
      } catch (GeolocationNotFoundException ignored) {}
    }

    GeolocationRequestDTO defaultCoords = getDefaultCoordinatesForCity(city.trim());
    if (defaultCoords != null) {
      return defaultCoords;
    }

    throw new GeolocationNotFoundException(
      String.format("Impossible de géolocaliser la ville '%s'. Vérifiez l'orthographe ou contactez l'administrateur.", city)
    );
  }

  public GeolocationRequestDTO getCoordinatesStrict(String query) {
    if (query == null || query.trim().isEmpty()) {
      throw new GeolocationNotFoundException("La requête de géolocalisation ne peut pas être vide");
    }

    return performGeocoding(query.trim());
  }

  public Geolocation createGeolocation(ActivitySaveRequestDTO dto) {
    if (dto.getAddress() == null || dto.getLocation().latitude() == 0 || dto.getLocation().longitude() == 0) {
      return null;
    }

    return geolocationRepository.save(new Geolocation(dto.getLocation().longitude(), dto.getLocation().latitude()));
  }

  private GeolocationRequestDTO performGeocoding(String query) {
    try {
      String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
      String url = GEOLOC_BASE_URL_GOUV + encodedQuery + "&limit=1";

      GeolocApiGouvResponseDTO response = restTemplate.getForObject(url, GeolocApiGouvResponseDTO.class);

      if (response != null && response.features() != null && !response.features().isEmpty()) {
        GeolocApiGouvResponseDTO.Feature feature = response.features().get(0);
        List<Double> coords = feature.geometry().coordinates();

        if (coords.size() >= INT) {
          double longitude = coords.get(0);
          double latitude = coords.get(1);

          if (isValidCoordinate(latitude, longitude)) {
            return new GeolocationRequestDTO(longitude, latitude);
          }
        }
      }

      throw new GeolocationNotFoundException("Aucun résultat trouvé pour: " + query);
    } catch (RestClientException e) {
      throw new GeolocationNotFoundException("Erreur lors de l'appel à l'API de géolocalisation: " + e.getMessage());
    } catch (Exception e) {
      throw new GeolocationNotFoundException("Erreur inattendue lors du géocodage: " + e.getMessage());
    }
  }

  private GeolocationRequestDTO getDefaultCoordinatesForCity(String city) {
    return cityCoordinatesCache.get(city.toLowerCase());
  }

  private boolean isValidCoordinate(double latitude, double longitude) {
    return latitude >= LATITUDE_MIN && latitude <= LATITUDE_MAX && longitude >= LONGITUDE_MIN && longitude <= LONGITUDE_MAX;
  }

  private Map<String, GeolocationRequestDTO> initializeCityCoordinates() {
    Map<String, GeolocationRequestDTO> coords = new HashMap<>();

    coords.put("paris", new GeolocationRequestDTO(PARIS_LONGITUDE, PARIS_LATITUDE));
    coords.put("lyon", new GeolocationRequestDTO(LYON_LONGITUDE, LYON_LATITUDE));
    coords.put("marseille", new GeolocationRequestDTO(MARSEILLE_LONGITUDE, MARSEILLE_LATITUDE));
    coords.put("toulouse", new GeolocationRequestDTO(TOULOUSE_LONGITUDE, TOULOUSE_LATITUDE));
    coords.put("nice", new GeolocationRequestDTO(NICE_LONGITUDE, NICE_LATITUDE));
    coords.put("nantes", new GeolocationRequestDTO(NANTES_LONGITUDE, NANTES_LATITUDE));
    coords.put("strasbourg", new GeolocationRequestDTO(STRASBOURG_LONGITUDE, STRASBOURG_LATITUDE));
    coords.put("montpellier", new GeolocationRequestDTO(MONTPELLIER_LONGITUDE, MONTPELLIER_LATITUDE));
    coords.put("bordeaux", new GeolocationRequestDTO(BORDEAUX_LONGITUDE, BORDEAUX_LATITUDE));
    coords.put("lille", new GeolocationRequestDTO(LILLE_LONGITUDE, LILLE_LATITUDE));
    coords.put("rennes", new GeolocationRequestDTO(RENNES_LONGITUDE, RENNES_LATITUDE));
    coords.put("reims", new GeolocationRequestDTO(REIMS_LONGITUDE, REIMS_LATITUDE));
    coords.put("saint-étienne", new GeolocationRequestDTO(SAINT_ETIENNE_LONGITUDE, SAINT_ETIENNE_LATITUDE));
    coords.put("saint-etienne", new GeolocationRequestDTO(SAINT_ETIENNE_LONGITUDE, SAINT_ETIENNE_LATITUDE));
    coords.put("toulon", new GeolocationRequestDTO(TOULON_LONGITUDE, TOULON_LATITUDE));
    coords.put("grenoble", new GeolocationRequestDTO(GRENOBLE_LONGITUDE, GRENOBLE_LATITUDE));
    coords.put("angers", new GeolocationRequestDTO(ANGERS_LONGITUDE, ANGERS_LATITUDE));
    coords.put("dijon", new GeolocationRequestDTO(DIJON_LONGITUDE, DIJON_LATITUDE));
    coords.put("brest", new GeolocationRequestDTO(BREST_LONGITUDE, BREST_LATITUDE));
    coords.put("clermont-ferrand", new GeolocationRequestDTO(CLERMONT_FERRAND_LONGITUDE, CLERMONT_FERRAND_LATITUDE));
    coords.put("tours", new GeolocationRequestDTO(TOURS_LONGITUDE, TOURS_LATITUDE));
    coords.put("amiens", new GeolocationRequestDTO(AMIENS_LONGITUDE, AMIENS_LATITUDE));
    coords.put("limoges", new GeolocationRequestDTO(LIMOGES_LONGITUDE, LIMOGES_LATITUDE));
    coords.put("annecy", new GeolocationRequestDTO(ANNECY_LONGITUDE, ANNECY_LATITUDE));
    coords.put("perpignan", new GeolocationRequestDTO(PERPIGNAN_LONGITUDE, PERPIGNAN_LATITUDE));
    coords.put("besançon", new GeolocationRequestDTO(BESANCON_LONGITUDE, BESANCON_LATITUDE));

    return coords;
  }
}
