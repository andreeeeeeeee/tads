package com.edulivre.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.edulivre.models.Curso;

public class CursoDAO {
  public static List<Curso> listar() {
    List<Curso> cursos = new ArrayList<>();
    String sql = "SELECT * FROM Curso;";
    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        Curso curso = new Curso();
        curso.setId(UUID.fromString(rs.getString("id")));
        curso.setTitulo(rs.getString("titulo"));
        curso.setDescricao(rs.getString("descricao"));
        curso.setDataCriacao(rs.getDate("data_criacao"));
        String avaliacaoStr = rs.getString("avaliacao");
        curso.setAvaliacao(avaliacaoStr != null ? new JSONObject(avaliacaoStr) : null);
        cursos.add(curso);
      }
      conexao.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return null;
    }
    return cursos;
  }

  public static Curso buscarPorTitulo(String titulo) {
    String sql = "SELECT * FROM curso WHERE titulo = ?;";
    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
      preparedStatement.setObject(1, titulo);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        Curso curso = new Curso();
        curso.setId(UUID.fromString(rs.getString("id")));
        curso.setTitulo(rs.getString("titulo"));
        curso.setDescricao(rs.getString("descricao"));
        curso.setDataCriacao(rs.getDate("data_criacao"));
        String avaliacaoStr = rs.getString("avaliacao");
        curso.setAvaliacao(avaliacaoStr != null ? new JSONObject(avaliacaoStr) : null);
        return curso;
      }
      conexao.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return null;
    }
    return null;
  }

  public static boolean inserirAvaliacao(Curso curso, JSONObject comentario) {
    String sql = "UPDATE curso SET avaliacao = ?::jsonb WHERE id = ?";
    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
      JSONArray comentarios = new JSONArray();
      JSONObject avaliacao = curso.getAvaliacao();

      if (avaliacao == null) {
        avaliacao = new JSONObject();
      } else {
        if (avaliacao.has("comentarios")) {
          JSONArray comentariosExistentes = avaliacao.getJSONArray("comentarios");
          for (int i = 0; i < comentariosExistentes.length(); i++) {
            comentarios.put(comentariosExistentes.getJSONObject(i));
          }
        }
      }

      comentarios.put(comentario);
      avaliacao.put("comentarios", comentarios);

      double somaNotas = 0;
      int totalAvaliacoes = comentarios.length();
      for (int i = 0; i < totalAvaliacoes; i++) {
        JSONObject comentarioAtual = comentarios.getJSONObject(i);
        if (comentarioAtual.has("nota")) {
          somaNotas += comentarioAtual.getDouble("nota");
        }
      }
      double media = totalAvaliacoes > 0 ? somaNotas / totalAvaliacoes : 0;
      avaliacao.put("media", media);

      preparedStatement.setString(1, avaliacao.toString());
      preparedStatement.setObject(2, curso.getId());
      int linhas = preparedStatement.executeUpdate();

      curso.setAvaliacao(avaliacao);

      System.out.println("Avaliação inserida com sucesso");
      return linhas == 1;
    } catch (SQLException e) {
      System.err.println("Erro ao inserir avaliação: " + e.getMessage());
      return false;
    }
  }

  public static boolean inserir(Curso curso) {
    String sql = "INSERT INTO curso (titulo, descricao, avaliacao) VALUES (?, ?, ?::jsonb);";
    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
      ;
      preparedStatement.setString(1, curso.getTitulo());
      preparedStatement.setString(2, curso.getDescricao());
      preparedStatement.setString(3, curso.getAvaliacao().toString());
      int linhas = preparedStatement.executeUpdate();

      conexao.close();

      return linhas == 1;
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return false;
    }
  }
}
