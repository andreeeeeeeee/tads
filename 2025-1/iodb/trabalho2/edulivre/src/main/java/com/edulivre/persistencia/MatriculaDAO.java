package com.edulivre.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.edulivre.negocio.Matricula;

public class MatriculaDAO {
  public static List<Matricula> listar() {
    List<Matricula> matriculas = new ArrayList<>();
    String sql = "SELECT * FROM matricula;";
    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        Matricula matricula = new Matricula();
        matricula.setId(rs.getInt("id"));
        matricula.setUsuarioID(UUID.fromString(rs.getString("usuario_id")));
        matricula.setCursoID(UUID.fromString(rs.getString("curso_id")));
        matricula.setDataMatricula(rs.getDate("data_matricula"));
        matriculas.add(matricula);
      }
      conexao.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
      return null;
    }
    return matriculas;
  }

  public static boolean inserir(Matricula matricula) {
    String sql = "INSERT INTO matricula (usuario_id, curso_id) VALUES (?, ?);";
    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
      preparedStatement.setObject(1, matricula.getUsuarioID());
      preparedStatement.setObject(2, matricula.getCursoID());
      int linhas = preparedStatement.executeUpdate();
      conexao.close();
      return linhas > 0;
    } catch (Exception e) {
      System.err.println(e.getMessage());
      return false;
    }
  }

  public static int contarMatriculasPorCurso(UUID cursoID) {
    String sql = "SELECT COUNT(id) FROM matricula WHERE curso_id = ?;";
    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
      preparedStatement.setObject(1, cursoID);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return rs.getInt(1);
      }
      conexao.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    return 0;
  }
}
