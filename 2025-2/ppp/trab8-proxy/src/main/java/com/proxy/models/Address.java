package com.proxy.models;

import org.json.JSONObject;

public class Address {
  private final String street;
  private final String suite;
  private final String city;
  private final String zipcode;
  private final Geo geo;

  public Address(JSONObject json) {
    this.street = json.getString("street");
    this.suite = json.getString("suite");
    this.city = json.getString("city");
    this.zipcode = json.getString("zipcode");
    this.geo = new Geo(json.getJSONObject("geo"));
  }

  @Override
  public String toString() {
    return "Address [street=" + street + ", suite=" + suite + ", city=" + city + ", zipcode=" + zipcode + ", geo=" + geo
        + "]";
  }

  public String getStreet() {
    return street;
  }

  public String getSuite() {
    return suite;
  }

  public String getCity() {
    return city;
  }

  public String getZipcode() {
    return zipcode;
  }

  public Geo getGeo() {
    return geo;
  }
}
