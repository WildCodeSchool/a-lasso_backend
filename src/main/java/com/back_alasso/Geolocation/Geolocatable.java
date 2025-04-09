package com.back_alasso.Geolocation;

import com.back_alasso.Address.Address;

public interface Geolocatable {
  Address getAddress();

  Geolocation getGeolocation();
}
