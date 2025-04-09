package com.back_alasso.Geolocation;

import com.back_alasso.Address.Address;
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
    String url =
      GEOLOC_BASE_URL_OPEN_STREET_MAP +
      address.getHouse_number() +
      ',' +
      address.getStreet_name() +
      ',' +
      address.getCity() +
      ',' +
      address.getCountry().getName();

    // Send Api External Request to obtain geolocation
    GeolocDTO[] response = restTemplate.getForObject(url, GeolocDTO[].class);

    if (response != null) {
      Geolocation geolocation = new Geolocation(response[0].longitude(), response[0].latitude());
      return geolocation;
    }
    throw new RuntimeException("Aucune donnée de géolocalisation trouvée");
  }
}
