package com.edulivre.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgreSQL {
  private final String host;
  private final String username;
  private final String dbname;
  private final String port;
  private final String password;

  public ConexaoPostgreSQL() {
    this.host = "localhost";
    this.port = "5432";
    this.username = "postgres";
    this.password = "postgres";
    this.dbname = "edulivre";
  }

  public Connection getConexao() {
    String url = String.format("jdbc:postgresql://%s:%s/%s", this.host, this.port, this.dbname);
    try {
      return DriverManager.getConnection(url, this.username, this.password);
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return null;
  }
}
