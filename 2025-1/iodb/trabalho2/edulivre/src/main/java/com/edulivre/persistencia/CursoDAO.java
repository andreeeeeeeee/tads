package com.edulivre.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.edulivre.negocio.Curso;

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
        curso.setAvaliacao((JSONObject) rs.getObject("avaliacao"));
        cursos.add(curso);
      }
      conexao.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return null;
    }
    return cursos;
  }

  public static boolean inserirAvaliacao(Curso curso, JSONObject comentario) {
    String select = String.format("SELECT avaliacao FROM curso WHERE id::text = ?");
    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(select)) {

      preparedStatement.setString(1, String.valueOf(curso.getId()));
      ResultSet rs = preparedStatement.executeQuery();
      JSONArray avaliacao = new JSONArray();
      if (rs.next()) {
        avaliacao = (JSONArray) rs.getObject("avaliacao");
      }
      avaliacao.put(comentario);

      String update = "UPDATE curso SET avaliacao = ?";
      PreparedStatement preparedStatement2 = conexao.prepareStatement(update);
      preparedStatement2.setObject(1, avaliacao, java.sql.Types.OTHER);
      int linhas = preparedStatement2.executeUpdate();

      conexao.close();
      System.out.println("updated");
      return linhas == 1;
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return false;
    }
  }

  public static boolean inserir(Curso curso) {
    String sql = "INSERT INTO curso (titulo, descricao, data_criacao) VALUES (?, ?, ?);";
    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

      preparedStatement.setString(1, curso.getTitulo());
      preparedStatement.setString(2, curso.getDescricao());
      preparedStatement.setDate(3, curso.getDataCriacao());
      // preparedStatement.setObject(4, curso.getAvaliacao());
      int linhas = preparedStatement.executeUpdate();

      conexao.close();

      return linhas == 1;
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return false;
    }
  }
}
