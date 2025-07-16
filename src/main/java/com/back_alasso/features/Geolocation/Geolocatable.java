package com.back_alasso.features.Geolocation;

import com.back_alasso.features.Address.Address;

public interface Geolocatable {
  Address getAddress();

  Geolocation getGeolocation();
}
