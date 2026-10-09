package persistencia;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import negocio.Location;

public class LocationMongoDAO {

  private static final String URI = "mongodb://localhost:27017";
  private static final String DATABASE = "tracking";
  private static final String COLLECTION = "locations";

  public void insert(Location localizacao) {
    try (MongoClient mongoClient = MongoClients.create(URI)) {
      collection(mongoClient).insertOne(toDocument(localizacao));
    }
  }

  public Location searchEarlier(String id) {
    try (MongoClient mongoClient = MongoClients.create(URI)) {
      Document doc = collection(mongoClient)
          .find(Filters.eq("id", id))
          .sort(Sorts.descending("dateTime"))
          .limit(1)
          .first();
      if (doc == null)
        return null;

      return toLocation(doc);
    }
  }

  public List<String> listIdsPreviousTo(LocalDateTime limit) {
    List<String> ides = new ArrayList<>();
    try (MongoClient mongoClient = MongoClients.create(URI)) {
      for (String id : collection(mongoClient).distinct("id",
          Filters.lt("dateTime", toDate(limit)),
          String.class)) {
        if (id != null && !id.isBlank())
          ides.add(id);
      }
    }
    return ides;
  }

  public long removePreviousTo(LocalDateTime corte) {
    try (MongoClient mongoClient = MongoClients.create(URI)) {
      return collection(mongoClient)
          .deleteMany(Filters.lt("dateTime", toDate(corte)))
          .getDeletedCount();
    }
  }

  private MongoCollection<Document> collection(MongoClient mongoClient) {
    MongoDatabase database = mongoClient.getDatabase(DATABASE);
    return database.getCollection(COLLECTION);
  }

  private Document toDocument(Location location) {
    Document doc = new Document();
    doc.append("id", location.getId());
    doc.append("latitude", location.getLatitude());
    doc.append("longitude", location.getLongitude());
    doc.append("dateTime", toDate(location.getDateTime()));
    return doc;
  }

  private Location toLocation(Document doc) {
    Location location = new Location();
    location.setId(doc.getString("id"));
    location.setLatitude(doc.get("latitude", Number.class).doubleValue());
    location.setLongitude(doc.get("longitude", Number.class).doubleValue());
    Date dateTime = doc.getDate("dateTime");
    location.setDateTime(LocalDateTime.ofInstant(dateTime.toInstant(), ZoneId.systemDefault()));
    return location;
  }

  private Date toDate(LocalDateTime dateTime) {
    return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
  }
}
