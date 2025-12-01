package com.javalin.trab2.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javalin.trab2.negocio.Ingresso;
import com.javalin.trab2.negocio.Poltrona;
import com.javalin.trab2.negocio.RelatorioOcupacao;

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
    String sql = "CALL remanejar_poltrona(?, ?, ?, ?)";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setString(1, cpf);
      stmt.setInt(2, sessaoId);
      stmt.setInt(3, poltronaAntigaId);
      stmt.setInt(4, poltronaNova);

      stmt.execute();
      return true;

    } catch (SQLException e) {
      throw new SQLException("Erro ao remanejar poltrona: " + e.getMessage(), e);
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
            COALESCE(
              (SELECT COUNT(*) FROM ingresso WHERE sessao_id = ? AND cpf IS NOT NULL)::FLOAT /
              NULLIF((SELECT sa.ocupacao
                      FROM sala sa
                      INNER JOIN sessao s ON sa.id = s.sala_id
                      WHERE s.id = ?), 0) * 100,
              0
            ) as percentual
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

  public int contarIngressosVendidos(int sessaoId) throws SQLException {
    String sql = "SELECT contar_ingressos_vendidos(?)";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setInt(1, sessaoId);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        return rs.getInt(1);
      }
    }
    return 0;
  }

  public List<Poltrona> listarAssentosDisponiveis(int sessaoId) throws SQLException {
    String sql = "SELECT poltrona_id, fileira, posicao, tipo FROM assentos_disponiveis(?)";
    List<Poltrona> poltronas = new ArrayList<>();

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setInt(1, sessaoId);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Poltrona poltrona = new Poltrona();
        poltrona.setId(rs.getInt("poltrona_id"));
        poltrona.setFileira(rs.getString("fileira"));
        poltrona.setNumero(rs.getInt("posicao"));
        poltronas.add(poltrona);
      }
    }
    return poltronas;
  }

  public boolean venderIngresso(String cpf, int sessaoId, int poltronaId, double valor) throws SQLException {
    String sql = "CALL vender_ingresso(?, ?, ?, ?)";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setString(1, cpf);
      stmt.setInt(2, sessaoId);
      stmt.setInt(3, poltronaId);
      stmt.setDouble(4, valor);

      stmt.execute();
      return true;
    } catch (SQLException e) {
      throw new SQLException("Erro ao vender ingresso: " + e.getMessage(), e);
    }
  }

  public RelatorioOcupacao gerarRelatorioOcupacao(int sessaoId) throws SQLException {
    String sql = "SELECT * FROM relatorio_ocupacao_sessao(?)";
    RelatorioOcupacao relatorio = null;

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setInt(1, sessaoId);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        relatorio = new RelatorioOcupacao();
        relatorio.setSessaoId(rs.getInt("sessao_id"));
        relatorio.setTituloFilme(rs.getString("titulo_filme"));
        relatorio.setSalaId(rs.getInt("sala_id"));
        relatorio.setCapacidade(rs.getInt("capacidade"));
        relatorio.setOcupados(rs.getInt("ocupados"));
        relatorio.setPercentual(rs.getDouble("percentual"));
      }
    }
    return relatorio;
  }
}
