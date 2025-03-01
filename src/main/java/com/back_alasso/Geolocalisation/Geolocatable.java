package com.back_alasso.Geolocalisation;

import com.back_alasso.Address.Address;

public interface Geolocatable {
  Address getAddress();

  Geolocalisation getGeolocalisation();
}
