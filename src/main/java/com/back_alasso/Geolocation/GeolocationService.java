package com.back_alasso.Geolocation;

import com.back_alasso.Address.Address;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeolocationService {

  private final String GEOLOC_BASE_URL_OPEN_STREET_MAP = "https://nominatim.openstreetmap.org/search?format=json&q=";
  private final RestTemplate restTemplate;

  public GeolocationService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public Geolocation getVoluntaryCoordinates(String city, String country) {
    String url = GEOLOC_BASE_URL_OPEN_STREET_MAP + city + ',' + country;

    // Send Api External Request to obtain geolocation
    GeolocDTO[] response = restTemplate.getForObject(url, GeolocDTO[].class);

    if (response != null) {
      Geolocation voluntaryGeolocation = new Geolocation(response[0].longitude(), response[0].latitude());
      return voluntaryGeolocation;
    }
    throw new RuntimeException("Aucune donnée de géolocalisation trouvée pour " + city + ", " + country);
  }

  public Geolocation getCoordinatesWithFullAddress(Address address) {
    try {
      String query = String.format(
        "%s %s %s %s",
        address.getHouse_number(),
        address.getStreet_name(),
        address.getCity(),
        address.getCountry().getName()
      );

      String encodedQuery = URLEncoder.encode(query, "UTF-8");

      String url = GEOLOC_BASE_URL_OPEN_STREET_MAP + encodedQuery;

      GeolocDTO[] response = restTemplate.getForObject(url, GeolocDTO[].class);

      if (response != null && response.length > 0) {
        return new Geolocation(response[0].longitude(), response[0].latitude());
      } else {
        return getVoluntaryCoordinates(address.getCity(), address.getCountry().getName());
      }
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Erreur d'encodage de l'adresse", e);
    }
  }
}
