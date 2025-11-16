package com.proxy.negocio;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.json.JSONObject;

public class UserAPIProxyCache implements API {
  public static final int MAX_CACHE = 3;

  private final UserAPI userAPI;
  private final LinkedHashMap<String, JSONObject> cache;
  private int size;

  public UserAPIProxyCache(UserAPI userAPI) {
    this.userAPI = userAPI;
    this.cache = new LinkedHashMap<>();
    this.size = 0;
  }

  @Override
  public JSONObject info(String userId) throws IOException, InterruptedException {
    if (cache.get(userId) == null) {
      System.out.println("Usuário " + userId + " não está em cache");

      if (size < MAX_CACHE) {
        cache.put(userId, this.userAPI.info(userId));
        this.size++;
        System.out.println("Adicionado ao cache (total: " + size + "/" + MAX_CACHE + ")");
      } else {
        String oldestKey = cache.entrySet().iterator().next().getKey();
        System.out.println("Cache cheio! Removendo usuário " + oldestKey + " e adicionando " + userId);
        cache.remove(oldestKey);
        cache.put(userId, this.userAPI.info(userId));
      }
    } else {
      System.out.println("Usuário " + userId + " recuperado do cache!");
    }

    return cache.get(userId);
  }
  
  public void cacheStatus() {
    System.out.println("\nStatus do cache:");
    System.out.println("Usuários em cache: " + cache.keySet());
    System.out.println("Tamanho atual: " + size + "/" + MAX_CACHE);
  }
}
