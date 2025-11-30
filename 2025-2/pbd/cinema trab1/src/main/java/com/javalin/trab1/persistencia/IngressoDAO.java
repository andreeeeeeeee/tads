package com.javalin.trab1.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.javalin.trab1.negocio.Ingresso;

public class IngressoDAO {

  private final ConexaoPostgreSQL conexaoBanco;

  public IngressoDAO() {
    this.conexaoBanco = new ConexaoPostgreSQL();
  }

  public void inserir(Ingresso ingresso) throws SQLException {
    String sql = "INSERT INTO ingresso (cpf, sessao_id, valor, poltrona_id) VALUES (?, ?, ?, ?)";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setString(1, ingresso.getCpf());
      stmt.setInt(2, ingresso.getSessaoId());
      stmt.setDouble(3, ingresso.getValor());
      stmt.setInt(4, ingresso.getPoltronaId());

      stmt.executeUpdate();
    }
  }

  public Ingresso buscarPorId(int id) throws SQLException {
    String sql = "SELECT id, cpf, sessao_id, valor, poltrona_id FROM ingresso WHERE id = ?";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        Ingresso ingresso = new Ingresso();
        ingresso.setId(rs.getInt("id"));
        ingresso.setCpf(rs.getString("cpf"));
        ingresso.setSessaoId(rs.getInt("sessao_id"));
        ingresso.setValor(rs.getDouble("valor"));
        ingresso.setPoltronaId(rs.getInt("poltrona_id"));
        return ingresso;
      }
    }
    return null;
  }

  public List<Ingresso> listarPorCpf(String cpf) throws SQLException {
    String sql = "SELECT id, cpf, sessao_id, valor, poltrona_id FROM ingresso WHERE cpf = ?";
    List<Ingresso> ingressos = new ArrayList<>();

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setString(1, cpf);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Ingresso ingresso = new Ingresso();
        ingresso.setId(rs.getInt("id"));
        ingresso.setCpf(rs.getString("cpf"));
        ingresso.setSessaoId(rs.getInt("sessao_id"));
        ingresso.setValor(rs.getDouble("valor"));
        ingresso.setPoltronaId(rs.getInt("poltrona_id"));
        ingressos.add(ingresso);
      }
    }
    return ingressos;
  }

  public boolean remanejarPoltrona(String cpf, int sessaoId, int poltronaAntigaId, int poltronaNova)
      throws SQLException {
    String sqlVerificarIngresso = """
        SELECT id FROM ingresso
        WHERE cpf = ? AND sessao_id = ? AND poltrona_id = ?
        """;

    String sqlVerificarDisponibilidade = """
        SELECT COUNT(*) as ocupada FROM ingresso
        WHERE sessao_id = ? AND poltrona_id = ?
        """;

    String sqlAtualizarIngresso = """
        UPDATE ingresso
        SET poltrona_id = ?
        WHERE cpf = ? AND sessao_id = ? AND poltrona_id = ?
        """;

    String sqlInserirLog = """
        INSERT INTO log_remanejamento (cpf, sessao_id, poltrona_antiga, poltrona_nova, data_remanejamento)
        VALUES (?, ?, ?, ?, ?)
        """;

    try (Connection conexao = conexaoBanco.getConexao()) {
      conexao.setAutoCommit(false);

      try {
        try (PreparedStatement stmt = conexao.prepareStatement(sqlVerificarIngresso)) {
          stmt.setString(1, cpf);
          stmt.setInt(2, sessaoId);
          stmt.setInt(3, poltronaAntigaId);
          ResultSet rs = stmt.executeQuery();
          if (!rs.next()) {
            throw new SQLException("Ingresso não encontrado para este CPF e poltrona");
          }
        }

        try (PreparedStatement stmt = conexao.prepareStatement(sqlVerificarDisponibilidade)) {
          stmt.setInt(1, sessaoId);
          stmt.setInt(2, poltronaNova);
          ResultSet rs = stmt.executeQuery();
          if (rs.next() && rs.getInt("ocupada") > 0) {
            throw new SQLException("Nova poltrona não está disponível");
          }
        }

        try (PreparedStatement stmt = conexao.prepareStatement(sqlAtualizarIngresso)) {
          stmt.setInt(1, poltronaNova);
          stmt.setString(2, cpf);
          stmt.setInt(3, sessaoId);
          stmt.setInt(4, poltronaAntigaId);

          int rowsAffected = stmt.executeUpdate();
          if (rowsAffected == 0) {
            throw new SQLException("Falha na atualização do ingresso");
          }
        }

        try (PreparedStatement stmt = conexao.prepareStatement(sqlInserirLog)) {
          stmt.setString(1, cpf);
          stmt.setInt(2, sessaoId);
          stmt.setInt(3, poltronaAntigaId);
          stmt.setInt(4, poltronaNova);
          stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
          stmt.executeUpdate();
        }

        conexao.commit();
        return true;

      } catch (SQLException e) {
        conexao.rollback();
        throw e;
      } finally {
        conexao.setAutoCommit(true);
      }
    }
  }

  public boolean isPoltronaOcupada(int sessaoId, int poltronaId) throws SQLException {
    String sql = "SELECT COUNT(*) as ocupada FROM ingresso WHERE sessao_id = ? AND poltrona_id = ?";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setInt(1, sessaoId);
      stmt.setInt(2, poltronaId);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        return rs.getInt("ocupada") > 0;
      }
    }
    return false;
  }

  public double calcularPercentualOcupacao(int sessaoId) throws SQLException {
    String sql = """
        SELECT
            COUNT(i.id)::FLOAT / p.total_poltronas * 100 as percentual
        FROM
            (SELECT COUNT(*) as total_poltronas
             FROM poltrona pol
             INNER JOIN sessao s ON pol.sala_id = s.sala_id
             WHERE s.id = ?) p
        LEFT JOIN ingresso i ON i.sessao_id = ?
        """;

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setInt(1, sessaoId);
      stmt.setInt(2, sessaoId);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        return rs.getDouble("percentual");
      }
    }
    return 0.0;
  }
}
