package com.edulivre.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;

import com.edulivre.DAOs.CursoDAO;
import com.edulivre.DAOs.MatriculaDAO;

public class Usuario {
  private UUID id;
  private String nome;
  private String email;
  private String senha;
  private Perfil perfil;

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public Perfil getPerfil() {
    return perfil;
  }

  public void setPerfil(Perfil perfil) {
    this.perfil = perfil;
  }

  public void addAvaliacao(Curso curso, float nota, String comentario, Date data) {
    JSONObject avaliacao = new JSONObject();
    avaliacao.put("usuario_id", this.id);
    avaliacao.put("nota", nota);
    avaliacao.put("comentario", comentario);
    avaliacao.put("data", data);

    CursoDAO.inserirAvaliacao(curso, avaliacao);
  }

  public List<Curso> obterCursosDisponiveis() {
    if (this.perfil == Perfil.ALUNO) {
      // Para alunos, retorna apenas os cursos em que estão matriculados
      return obterCursosMatriculados();
    } else {
      // Para professores e admins, retorna todos os cursos
      return CursoDAO.listar();
    }
  }

  private List<Curso> obterCursosMatriculados() {
    List<Curso> cursosMatriculados = new ArrayList<>();
    List<Matricula> matriculas = MatriculaDAO.listar();

    if (matriculas != null) {
      for (Matricula matricula : matriculas) {
        if (matricula.getUsuarioID().equals(this.id)) {
          // Buscar o curso correspondente à matrícula
          List<Curso> todosCursos = CursoDAO.listar();
          if (todosCursos != null) {
            for (Curso curso : todosCursos) {
              if (curso.getId().equals(matricula.getCursoID())) {
                cursosMatriculados.add(curso);
                break;
              }
            }
          }
        }
      }
    }

    return cursosMatriculados;
  }
}
