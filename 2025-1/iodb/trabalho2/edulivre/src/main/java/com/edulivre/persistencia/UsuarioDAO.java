package com.edulivre.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.edulivre.negocio.Perfil;
import com.edulivre.negocio.Usuario;

public class UsuarioDAO {
  public List<Usuario> listar() {
    List<Usuario> usuarios = new ArrayList<>();
    String sql = "SELECT * FROM usuario;";
    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.fromString(rs.getString("id")));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setPerfil(Perfil.fromString(rs.getString("perfil")));
        usuario.setSenha(rs.getString("senha"));
        usuarios.add(usuario);
      }
      conexao.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return null;
    }
    return usuarios;
  }

  public static Usuario buscarPorEmail(String email) {
    String sql = "SELECT * FROM usuario WHERE email = ?;";
    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
      preparedStatement.setString(1, email);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.fromString(rs.getString("id")));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setPerfil(Perfil.fromString(rs.getString("perfil")));
        usuario.setSenha(rs.getString("senha"));
        return usuario;
      }
      conexao.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return null;
    }
    return null;
  }

  public static boolean inserir(Usuario usuario) {
    String sql = "INSERT INTO usuario (nome, email, senha, perfil) VALUES (?, ?, ?, ?);";
    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

      preparedStatement.setString(1, usuario.getNome());
      preparedStatement.setString(2, usuario.getEmail());
      preparedStatement.setString(3, usuario.getSenha());
      preparedStatement.setString(4, usuario.getPerfil().getDescricao());
      int linhas = preparedStatement.executeUpdate();
      conexao.close();

      return linhas == 1;
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return false;
    }
  }
}
