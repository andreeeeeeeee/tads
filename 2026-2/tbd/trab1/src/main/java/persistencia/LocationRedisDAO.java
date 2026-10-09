package persistencia;

import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import negocio.Location;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import util.LocalDateTimeTypeAdapter;

public class LocationRedisDAO {

  private static final String HOST = "localhost";
  private static final int PORT = 6379;
  private static final int INDEX = 0;
  private static final int TTL_SEGUNDOS = 300;
  private static final String PREFIXO = "vehicle:";

  private final Gson gson;

  public LocationRedisDAO() {
    this.gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
        .create();
  }

  public void save(Location location) {
    try (JedisPool pool = new JedisPool(new JedisPoolConfig(), HOST, PORT)) {
      try (Jedis jedis = pool.getResource()) {
        jedis.select(INDEX);
        jedis.setex(key(location.getId()), TTL_SEGUNDOS, gson.toJson(location));
      }
    }
  }

  public Location get(String id) {
    try (JedisPool pool = new JedisPool(new JedisPoolConfig(), HOST, PORT)) {
      try (Jedis jedis = pool.getResource()) {
        jedis.select(INDEX);
        String json = jedis.get(key(id));
        if (json == null)
          return null;
        return gson.fromJson(json, Location.class);
      }
    }
  }

  public boolean remove(String id) {
    try (JedisPool pool = new JedisPool(new JedisPoolConfig(), HOST, PORT)) {
      try (Jedis jedis = pool.getResource()) {
        jedis.select(INDEX);
        return jedis.del(key(id)) > 0;
      }
    }
  }

  private String key(String id) {
    return PREFIXO + id;
  }
}
