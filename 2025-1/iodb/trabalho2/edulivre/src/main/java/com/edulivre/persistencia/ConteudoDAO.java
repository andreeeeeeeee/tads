package com.edulivre.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.edulivre.negocio.Conteudo;
import com.edulivre.negocio.Tipo;

public interface ConteudoDAO {
  public static List<Conteudo> listar() {
    List<Conteudo> conteudos = new ArrayList<>();
    String sql = "SELECT * FROM conteudo;";
    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        Conteudo conteudo = new Conteudo();
        conteudo.setId(rs.getInt("id"));
        conteudo.setCursoID(UUID.fromString(rs.getString("curso_id")));
        conteudo.setTitulo(rs.getString("titulo"));
        conteudo.setDescricao(rs.getString("descricao"));
        conteudo.setTipo(Tipo.fromString(rs.getString("tipo")));
        conteudo.setArquivo(rs.getBytes("arquivo"));
        conteudos.add(conteudo);
      }
      conexao.close();
    } catch (Exception e) {
      System.err.println("Erro ao listar conteúdos: " + e.getMessage());
      return null;
    }
    return conteudos;
  }

  public static boolean inserir(Conteudo conteudo) {
    String sql = "INSERT INTO conteudo (curso_id, titulo, descricao, tipo, arquivo) VALUES (?, ?, ?, ?, ?);";
    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
      preparedStatement.setObject(1, conteudo.getCursoID());
      preparedStatement.setString(2, conteudo.getTitulo());
      preparedStatement.setString(3, conteudo.getDescricao());
      preparedStatement.setString(4, conteudo.getTipo().getDescricao());
      preparedStatement.setBytes(5, conteudo.getArquivo());
      int linhas = preparedStatement.executeUpdate();
      conexao.close();
      return linhas == 1;
    } catch (Exception e) {
      System.err.println("Erro ao inserir conteúdo: " + e.getMessage());
      return false;
    }
  }

  public static List<Conteudo> buscarPorCurso(UUID cursoId) {
    List<Conteudo> conteudos = new ArrayList<>();
    String sql = "SELECT * FROM conteudo WHERE curso_id = ?;";
    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
      preparedStatement.setObject(1, cursoId);
      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        Conteudo conteudo = new Conteudo();
        conteudo.setId(rs.getInt("id"));
        conteudo.setCursoID(UUID.fromString(rs.getString("curso_id")));
        conteudo.setTitulo(rs.getString("titulo"));
        conteudo.setDescricao(rs.getString("descricao"));
        conteudo.setTipo(Tipo.fromString(rs.getString("tipo")));
        conteudo.setArquivo(rs.getBytes("arquivo"));
        conteudos.add(conteudo);
      }
      conexao.close();
    } catch (Exception e) {
      System.err.println("Erro ao buscar conteúdos do curso: " + e.getMessage());
      return null;
    }
    return conteudos;
  }

  public static void buscarConteudosComTipoETamanho(UUID cursoId) {
    List<Conteudo> conteudos = buscarPorCurso(cursoId);

    if (conteudos == null || conteudos.isEmpty()) {
      System.out.println("Nenhum conteúdo encontrado para este curso.");
      return;
    }

    System.out.println("=== CONTEÚDOS DO CURSO ===");
    System.out.printf("%-5s %-30s %-15s %-15s%n", "ID", "Título", "Tipo", "Tamanho");
    System.out.println("─".repeat(70));

    for (Conteudo conteudo : conteudos) {
      String tamanho = "N/A";
      if (conteudo.getArquivo() != null) {
        long bytes = conteudo.getArquivo().length;
        tamanho = formatarTamanho(bytes);
      }

      System.out.printf("%-5d %-30s %-15s %-15s%n",
          conteudo.getId(),
          conteudo.getTitulo(),
          conteudo.getTipo().getDescricao(),
          tamanho);
    }
  }

  public static void buscarConteudosPorTituloCurso(String tituloCurso) {
    String sql = """
        SELECT c.id, c.curso_id, c.titulo, c.descricao, c.tipo, c.arquivo
        FROM conteudo c
        INNER JOIN curso cur ON c.curso_id = cur.id
        WHERE cur.titulo = ?
        """;

    try (Connection conexao = new ConexaoPostgreSQL().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
      preparedStatement.setString(1, tituloCurso);
      ResultSet rs = preparedStatement.executeQuery();

      System.out.println("=== CONTEÚDOS DO CURSO: " + tituloCurso + " ===");
      System.out.printf("%-5s %-30s %-15s %-15s%n", "ID", "Título", "Tipo", "Tamanho");
      System.out.println("─".repeat(70));

      boolean encontrou = false;
      while (rs.next()) {
        encontrou = true;
        int id = rs.getInt("id");
        String titulo = rs.getString("titulo");
        String tipo = rs.getString("tipo");
        byte[] arquivo = rs.getBytes("arquivo");

        String tamanho = "N/A";
        if (arquivo != null) {
          tamanho = formatarTamanho(arquivo.length);
        }

        System.out.printf("%-5d %-30s %-15s %-15s%n", id, titulo, tipo, tamanho);
      }

      if (!encontrou) {
        System.out.println("Nenhum conteúdo encontrado para o curso: " + tituloCurso);
      }

      conexao.close();
    } catch (Exception e) {
      System.err.println("Erro ao buscar conteúdos por título do curso: " + e.getMessage());
    }
  }

  private static String formatarTamanho(long bytes) {
    if (bytes == 0)
      return "0 B";

    String[] unidades = { "B", "KB", "MB", "GB" };
    int unidade = 0;
    double tamanho = bytes;

    while (tamanho >= 1024 && unidade < unidades.length - 1) {
      tamanho /= 1024;
      unidade++;
    }

    return String.format("%.2f %s", tamanho, unidades[unidade]);
  }
}
