package com.edulivre.apresentacao;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import com.edulivre.negocio.Curso;
import com.edulivre.negocio.Perfil;
import com.edulivre.negocio.Usuario;
import com.edulivre.persistencia.CursoDAO;
import com.edulivre.persistencia.UsuarioDAO;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");
    Curso cursoJava = new Curso();
    cursoJava.setId(UUID.randomUUID());
    cursoJava.setTitulo("Curso de Java");
    cursoJava.setDescricao("Java básico ao avançado");
    // yyyy-[m]m-[d]d
    cursoJava.setDataCriacao(Date.valueOf("2025-06-30"));

    Usuario usuario = new Usuario();
    usuario.setId(UUID.randomUUID());
    usuario.setNome("André");
    usuario.setEmail("andre@gmail.com");
    usuario.setPerfil(Perfil.ADMIN);
    usuario.setSenha("12345");

    CursoDAO.inserir(cursoJava);
    UsuarioDAO.inserir(usuario);

    List<Curso> cursos = CursoDAO.listar();

    for (Curso curso : cursos) {
      System.out.println("Curso: " + curso.getTitulo()+"\n"+curso.getAvaliacao().getString("media"));
    }

    usuario.addAvaliacao(cursoJava, 5, "òtimo", Date.valueOf("2025-06-30"));

  }
}