package com.proxy.negocio;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.json.JSONObject;

public class UserAPI implements API {

  @Override
  public JSONObject info(String userId) throws IOException, InterruptedException {
    System.out.println("Consultando API para usuário: " + userId);

    HttpClient client = HttpClient.newHttpClient();
    String url = "https://jsonplaceholder.typicode.com/users/" + userId;

    HttpRequest request = HttpRequest.newBuilder()
        .GET()
        .timeout(Duration.ofSeconds(10))
        .uri(URI.create(url))
        .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() != 200) {
      throw new IOException("Erro na API: " + response.statusCode());
    }

    return new JSONObject(response.body());
  }
}
