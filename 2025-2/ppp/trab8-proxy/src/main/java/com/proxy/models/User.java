package com.proxy.models;

import org.json.JSONObject;

public class User {
  private final int id;
  private final String name;
  private final String username;
  private final String email;
  private final Address address;
  private final String phone;
  private final String website;
  private final Company company;

  public User(JSONObject json) {
    this.id = json.getInt("id");
    this.name = json.getString("name");
    this.username = json.getString("username");
    this.email = json.getString("email");
    this.address = new Address(json.getJSONObject("address"));
    this.phone = json.getString("phone");
    this.website = json.getString("website");
    this.company = new Company(json.getJSONObject("company"));
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", address=" + address
        + ", phone=" + phone + ", website=" + website + ", company=" + company + "]";
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public Address getAddress() {
    return address;
  }

  public String getPhone() {
    return phone;
  }

  public String getWebsite() {
    return website;
  }

  public Company getCompany() {
    return company;
  }
}
