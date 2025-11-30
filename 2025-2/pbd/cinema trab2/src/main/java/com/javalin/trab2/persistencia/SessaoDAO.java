package com.javalin.trab2.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javalin.trab2.negocio.ConflitoDeSessao;
import com.javalin.trab2.negocio.Sessao;

public class SessaoDAO {

  private final ConexaoPostgreSQL conexaoBanco;

  public SessaoDAO() {
    this.conexaoBanco = new ConexaoPostgreSQL();
  }

  public List<Sessao> listarSessoesDisponiveis() throws SQLException {
    String sql = """
        SELECT id, filme_id, sala_id, data, hora_inicio, hora_fim
        FROM sessao
        WHERE data >= CURRENT_DATE
        ORDER BY data, hora_inicio
        """;

    List<Sessao> sessoes = new ArrayList<>();

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Sessao sessao = new Sessao();
        sessao.setId(rs.getInt("id"));
        sessao.setFilmeId(rs.getInt("filme_id"));
        sessao.setSalaId(rs.getInt("sala_id"));
        java.sql.Date data = rs.getDate("data");
        java.sql.Time horaInicio = rs.getTime("hora_inicio");
        if (data != null && horaInicio != null) {
          sessao.setHorario(data.toLocalDate().atTime(horaInicio.toLocalTime()));
        }
        sessao.setValor(25.00);
        sessoes.add(sessao);
      }
    }
    return sessoes;
  }

  public Sessao buscarPorId(int id) throws SQLException {
    String sql = "SELECT id, filme_id, sala_id, data, hora_inicio, hora_fim FROM sessao WHERE id = ?";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        Sessao sessao = new Sessao();
        sessao.setId(rs.getInt("id"));
        sessao.setFilmeId(rs.getInt("filme_id"));
        sessao.setSalaId(rs.getInt("sala_id"));
        java.sql.Date data = rs.getDate("data");
        java.sql.Time horaInicio = rs.getTime("hora_inicio");
        if (data != null && horaInicio != null) {
          sessao.setHorario(data.toLocalDate().atTime(horaInicio.toLocalTime()));
        }
        sessao.setValor(25.00); 
        return sessao;
      }
    }
    return null;
  }

  public List<ConflitoDeSessao> verificarSessoesSimultaneas() throws SQLException {
    String sql = """
        SELECT sessao_a, sessao_b, filme_id, titulo, hora_inicio, sala_a, sala_b
        FROM sessoes_conflitantes
        """;

    List<ConflitoDeSessao> conflitos = new ArrayList<>();

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        ConflitoDeSessao conflito = new ConflitoDeSessao();
        conflito.setSessaoId1(rs.getInt("sessao_a"));
        conflito.setSessaoId2(rs.getInt("sessao_b"));
        conflito.setFilmeId(rs.getInt("filme_id"));
        conflito.setTituloFilme(rs.getString("titulo"));
        conflito.setSalaId1(rs.getInt("sala_a"));
        conflito.setSalaId2(rs.getInt("sala_b"));
        java.sql.Time hora = rs.getTime("hora_inicio");
        if (hora != null) {
          conflito.setHorario(java.time.LocalDate.now().atTime(hora.toLocalTime()));
        }
        conflitos.add(conflito);
      }
    }
    return conflitos;
  }

  public int simularOcupacaoSessao(int sessaoId, double percentualOcupacao) throws SQLException {
    String sql = "SELECT simular_ocupacao_sessao(?, ?::numeric) as poltronas_simuladas";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setInt(1, sessaoId);
      stmt.setDouble(2, percentualOcupacao);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        return rs.getInt("poltronas_simuladas");
      }
    }
    return 0;
  }

  public int ajustarPrecosBaixaOcupacao() throws SQLException {
    String sql = "SELECT ajustar_precos_baixa_ocupacao() as ingressos_ajustados";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        return rs.getInt("ingressos_ajustados");
      }
    }
    return 0;
  }
}
