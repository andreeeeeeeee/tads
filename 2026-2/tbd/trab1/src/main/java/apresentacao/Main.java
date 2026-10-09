package apresentacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import negocio.Location;
import persistencia.LocationMongoDAO;
import persistencia.LocationRedisDAO;

public class Main {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

  public static void main(String[] args) {
    LocationMongoDAO mongoDAO = new LocationMongoDAO();
    LocationRedisDAO redisDAO = new LocationRedisDAO();
    Scanner scanner = new Scanner(System.in);

    boolean executing = true;
    while (executing) {
      showMenu();
      String option = scanner.nextLine().trim();
      switch (option) {
        case "1" -> registerTelemetry(scanner, mongoDAO, redisDAO);
        case "2" -> checkLocation(scanner, mongoDAO, redisDAO);
        case "3" -> clearHistory(scanner, mongoDAO, redisDAO);
        case "0" -> {
          executing = false;
          System.out.println("Encerrando o sistema.");
        }
        default -> System.out.println("Opção inválida.");
      }
    }
  }

  private static void showMenu() {
    System.out.println();
    System.out.println("=== Sistema de Rastreamento de Veículos ===");
    System.out.println("1 - Registrar telemetria");
    System.out.println("2 - Consultar localização");
    System.out.println("3 - Limpar histórico antigo");
    System.out.println("0 - Sair");
    System.out.print("Opção: ");
  }

  private static void registerTelemetry(Scanner scanner, LocationMongoDAO mongoDAO,
      LocationRedisDAO redisDAO) {
    try {
      String id = readId(scanner);
      if (id == null)
        return;

      Double latitude = readDecimal(scanner, "Latitude (-90 a 90): ", -90, 90);
      if (latitude == null)
        return;

      Double longitude = readDecimal(scanner, "Longitude (-180 a 180): ", -180, 180);
      if (longitude == null)
        return;

      Location location = new Location();
      location.setId(id);
      location.setLatitude(latitude);
      location.setLongitude(longitude);
      location.setDateTime(LocalDateTime.now());

      redisDAO.save(location);
      mongoDAO.insert(location);
      System.out.println("Telemetria registrada no Redis (5 minutos) e no histórico do MongoDB.");
    } catch (Exception e) {
      System.out.println("Falha ao registrar telemetria. Verifique se o Redis e o MongoDB estão em execução.");
      System.out.println("Detalhe: " + e.getMessage());
    }
  }

  private static void checkLocation(Scanner scanner, LocationMongoDAO mongoDAO,
      LocationRedisDAO redisDAO) {
    try {
      String id = readId(scanner);
      if (id == null)
        return;

      Location location = redisDAO.get(id);
      if (location != null) {
        origin(location, "Redis (cache)");
        return;
      }

      location = mongoDAO.searchEarlier(id);
      if (location == null) {
        System.out.println("Nenhuma localização encontrada para o veículo " + id + ".");
        return;
      }

      redisDAO.save(location);
      origin(location, "MongoDB (histórico)");
      System.out.println("Posição recarregada no Redis com expiração de 5 minutos.");
    } catch (Exception e) {
      System.out.println("Falha ao consultar localização. Verifique se o Redis e o MongoDB estão em execução.");
      System.out.println("Detalhe: " + e.getMessage());
    }
  }

  private static void clearHistory(Scanner scanner, LocationMongoDAO mongoDAO,
      LocationRedisDAO redisDAO) {
    try {
      System.out.print("Apagar localizações mais antigas que quantos dias? ");
      String text = scanner.nextLine().trim();
      int days;
      try {
        days = Integer.parseInt(text);
      } catch (NumberFormatException e) {
        System.out.println("Informe um número inteiro de dias.");
        return;
      }
      if (days <= 0) {
        System.out.println("A quantidade de dias deve ser maior que zero.");
        return;
      }

      LocalDateTime limit = LocalDateTime.now().minusDays(days);
      List<String> ids = mongoDAO.listIdsPreviousTo(limit);
      long removed = mongoDAO.removePreviousTo(limit);

      int removedKeys = 0;
      for (String id : ids)
        if (redisDAO.remove(id))
          removedKeys++;

      System.out.println("Documentos removidos do MongoDB: " + removed);
      System.out.println("Chaves removidas do Redis: " + removedKeys);
    } catch (Exception e) {
      System.out.println("Falha ao limpar o histórico. Verifique se o Redis e o MongoDB estão em execução.");
      System.out.println("Detalhe: " + e.getMessage());
    }
  }

  private static String readId(Scanner scanner) {
    System.out.print("id do veículo: ");
    String id = scanner.nextLine().trim();
    if (id.isEmpty()) {
      System.out.println("O id do veículo não pode ser vazio.");
      return null;
    }
    return id;
  }

  private static Double readDecimal(Scanner scanner, String prompt, double min, double max) {
    System.out.print(prompt);
    String texto = scanner.nextLine().trim().replace(',', '.');
    double value;
    try {
      value = Double.parseDouble(texto);
    } catch (NumberFormatException e) {
      System.out.println("Informe um número válido.");
      return null;
    }
    if (value < min || value > max) {
      System.out.println("Valor fora do intervalo permitido.");
      return null;
    }
    return value;
  }

  private static void origin(Location location, String origin) {
    System.out.println("Origem: " + origin);
    System.out.println("Veículo: " + location.getId());
    System.out.println("Latitude: " + location.getLatitude());
    System.out.println("Longitude: " + location.getLongitude());
    System.out.println("Data/hora: " + FORMATTER.format(location.getDateTime()));
  }
}
