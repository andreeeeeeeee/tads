package com.javalin.trab2.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javalin.trab2.negocio.Filme;

public class FilmeDAO {

  private final ConexaoPostgreSQL conexaoBanco;

  public FilmeDAO() {
    this.conexaoBanco = new ConexaoPostgreSQL();
  }

  public List<Filme> listar() throws SQLException {
    String sql = "SELECT id, titulo, duracao, classificacao_etaria, sinopse FROM filme ORDER BY titulo";
    List<Filme> filmes = new ArrayList<>();

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Filme filme = new Filme();
        filme.setId(rs.getInt("id"));
        filme.setTitulo(rs.getString("titulo"));
        filme.setDuracaoMinutos(rs.getInt("duracao"));
        filme.setClassificacaoEtaria(rs.getString("classificacao_etaria"));
        filme.setSinopse(rs.getString("sinopse"));
        filmes.add(filme);
      }
    }
    return filmes;
  }

  public Filme buscarPorId(int id) throws SQLException {
    String sql = "SELECT id, titulo, duracao, classificacao_etaria, sinopse FROM filme WHERE id = ?";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        Filme filme = new Filme();
        filme.setId(rs.getInt("id"));
        filme.setTitulo(rs.getString("titulo"));
        filme.setDuracaoMinutos(rs.getInt("duracao"));
        filme.setClassificacaoEtaria(rs.getString("classificacao_etaria"));
        filme.setSinopse(rs.getString("sinopse"));
        return filme;
      }
    }
    return null;
  }

  public boolean adicionar(String titulo, int duracao, String classificacao, String sinopse) throws SQLException {
    String sql = "SELECT adicionar_filme(?, ?, ?, ?) as sucesso";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setString(1, titulo);
      stmt.setInt(2, duracao);
      stmt.setString(3, classificacao);
      stmt.setString(4, sinopse);

      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return rs.getBoolean("sucesso");
      }
    }
    return false;
  }

  public boolean excluir(int id) throws SQLException {
    String sql = "SELECT excluir_filme(?) as sucesso";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setInt(1, id);

      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return rs.getBoolean("sucesso");
      }
    }
    return false;
  }

  public List<String> buscarPorDiretor(String nomeDiretor) throws SQLException {
    String sql = "SELECT * FROM buscar_filme_por_diretor(?)";
    List<String> titulos = new ArrayList<>();

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setString(1, nomeDiretor);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        titulos.add(rs.getString("titulo"));
      }
    }
    return titulos;
  }
}
