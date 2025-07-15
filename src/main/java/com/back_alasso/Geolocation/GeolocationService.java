package com.back_alasso.Geolocation;

import com.back_alasso.Activity.DTO.ActivitySaveRequestDTO;
import com.back_alasso.Geolocation.DTO.GeolocationRequestDTO;
import com.back_alasso.Geolocation.DTO.GouvGeolocDTO;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeolocationService {

  private final String GEOLOC_BASE_URL_GOUV = "https://api-adresse.data.gouv.fr/search/?q=";
  private final RestTemplate restTemplate;
  private final GeolocationRepository geolocationRepository;

  public GeolocationService(RestTemplate restTemplate, GeolocationRepository geolocationRepository) {
    this.restTemplate = restTemplate;
    this.geolocationRepository = geolocationRepository;
  }

  public GeolocationRequestDTO getVoluntaryCoordinates(String city, String country) {
    String query = String.format("%s, %s", city, country);
    String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
    String url = GEOLOC_BASE_URL_GOUV + encodedQuery + "&limit=1";

    GouvGeolocDTO response = restTemplate.getForObject(url, GouvGeolocDTO.class);

    if (response != null && response.features() != null && !response.features().isEmpty()) {
      GouvGeolocDTO.Feature feature = response.features().get(0);
      List<Double> coords = feature.geometry().coordinates();
      double lon = coords.get(0);
      double lat = coords.get(1);

      return new GeolocationRequestDTO(lon, lat);
    } else {
      throw new RuntimeException("Aucune donnée de géolocalisation trouvée pour " + city + ", " + country);
    }
  }

  public Geolocation createGeolocation(ActivitySaveRequestDTO dto) {
    if (dto.getAddress() == null || dto.getLocation().latitude() == 0 || dto.getLocation().longitude() == 0) {
      return null;
    }

    return geolocationRepository.save(new Geolocation(dto.getLocation().longitude(), dto.getLocation().latitude()));
  }
}
