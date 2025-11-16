package com.proxy.models;

import org.json.JSONObject;

public class Company {
  private final String name;
  private final String catchPhrase;
  private final String bs;

  public Company(JSONObject json) {
    this.name = json.getString("name");
    this.catchPhrase = json.getString("catchPhrase");
    this.bs = json.getString("bs");
  }

  @Override
  public String toString() {
    return "Company [name=" + name + ", catchPhrase=" + catchPhrase + ", bs=" + bs + "]";
  }

  public String getName() {
    return name;
  }

  public String getCatchPhrase() {
    return catchPhrase;
  }

  public String getBs() {
    return bs;
  }
}