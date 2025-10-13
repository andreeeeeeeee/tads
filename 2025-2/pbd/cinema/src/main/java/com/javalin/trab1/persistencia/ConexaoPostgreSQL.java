package com.javalin.trab1.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgreSQL {
  private String host;
  private String dbname;
  private String password;
  private String port;
  private String username;

  public ConexaoPostgreSQL() {
    this.host = "localhost";
    this.dbname = "cinema";
    this.port = "5432";
    this.username = "postgres";
    this.password = "postgres";
  }

  public ConexaoPostgreSQL(String host, String dbname, String port,
      String username, String password) {
    this.host = host;
    this.dbname = dbname;
    this.port = port;
    this.username = username;
    this.password = password;
  }

  public Connection getConexao() {
    String url = "jdbc:postgresql://" + this.host + ":" + this.port + "/" + dbname;
    try {
      return DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      System.out.println("Erro na conexão com o banco de dados!");
      e.printStackTrace();
      throw new RuntimeException("Falha na conexão com o banco de dados", e);
    }
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getDbname() {
    return dbname;
  }

  public void setDbname(String dbname) {
    this.dbname = dbname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
