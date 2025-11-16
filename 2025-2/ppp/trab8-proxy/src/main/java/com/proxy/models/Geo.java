package com.proxy.models;

import org.json.JSONObject;

public class Geo {
  private final String lat;
  private final String lng;

  public Geo(JSONObject json) {
    this.lat = json.getString("lat");
    this.lng = json.getString("lng");
  }

  @Override
  public String toString() {
    return "Geo [lat=" + lat + ", lng=" + lng + "]";
  }

  public String getLat() {
    return lat;
  }

  public String getLng() {
    return lng;
  }
}
