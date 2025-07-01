package com.edulivre.models;

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
      return switch (descricao) {
          case "aluno" -> ALUNO;
          case "professor" -> PROFESSOR;
          case "admin" -> ADMIN;
          default -> null;
      };
  }
}