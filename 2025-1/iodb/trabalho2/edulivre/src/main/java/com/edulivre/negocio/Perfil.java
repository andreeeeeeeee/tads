package com.edulivre.negocio;

public enum Perfil {
  ALUNO("aluno"),
  PROFESSOR("professor"),
  ADMIN("admin");

  private final String descricao;

  Perfil(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public static Perfil fromString(String descricao) {
    switch (descricao) {
      case "aluno":
        return ALUNO;
      case "professor":
        return PROFESSOR;
      case "admin":
        return ADMIN;

      default:
        return null;
    }
  }
}