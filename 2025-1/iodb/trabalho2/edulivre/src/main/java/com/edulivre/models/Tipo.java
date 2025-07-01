package com.edulivre.models;

public enum Tipo {
  VIDEO("video"),
  PDF("pdf"),
  IMAGEM("imagem"),
  AUDIO("audio"),
  QUIZ("quiz"),
  SLIDE("slide");

  private final String descricao;

  Tipo(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public static Tipo fromString(String descricao) {
      return switch (descricao) {
          case "video" -> VIDEO;
          case "pdf" -> PDF;
          case "imagem" -> IMAGEM;
          case "audio" -> AUDIO;
          case "quiz" -> QUIZ;
          case "slide" -> SLIDE;
          default -> null;
      };
  }
}