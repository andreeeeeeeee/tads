package com.proxy.negocio;

import java.io.IOException;

import org.json.JSONObject;

public interface API {
  public JSONObject info(String id) throws IOException, InterruptedException;
}
