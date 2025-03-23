package com.back_alasso.Geolocalisation;

import com.back_alasso.Address.Address;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeolocalisationService {

  private final String GEOLOC_BASE_URL_OPEN_STREET_MAP = "https://nominatim.openstreetmap.org/search?format=json&q=";
  private final RestTemplate restTemplate;

  public GeolocalisationService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public Geolocalisation getVoluntaryCoordinates(String city, String country) {
    String url = GEOLOC_BASE_URL_OPEN_STREET_MAP + city + ',' + country;

    // Send Api External Request to obtain geolocalisation
    GeolocDTO[] response = restTemplate.getForObject(url, GeolocDTO[].class);

    if (response != null) {
      Geolocalisation voluntaryGeolocalisation = new Geolocalisation(response[0].longitude(), response[0].latitude());
      return voluntaryGeolocalisation;
    }
    throw new RuntimeException("Aucune donnée de géolocalisation trouvée pour " + city + ", " + country);
  }

  public Geolocalisation getCoordinatesWithFullAddress(Address address) {
    String url =
      GEOLOC_BASE_URL_OPEN_STREET_MAP +
      address.getHouse_number() +
      ',' +
      address.getStreet_name() +
      ',' +
      address.getCity() +
      ',' +
      address.getCountry().getName();

    // Send Api External Request to obtain geolocalisation
    GeolocDTO[] response = restTemplate.getForObject(url, GeolocDTO[].class);

    if (response != null) {
      Geolocalisation geolocalisation = new Geolocalisation(response[0].longitude(), response[0].latitude());
      return geolocalisation;
    }
    throw new RuntimeException("Aucune donnée de géolocalisation trouvée");
  }
}
