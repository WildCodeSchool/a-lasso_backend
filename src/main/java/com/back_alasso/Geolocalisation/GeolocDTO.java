package com.back_alasso.Geolocalisation;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GeolocDTO(@JsonProperty("lon") double longitude, @JsonProperty("lat") double latitude) {}
