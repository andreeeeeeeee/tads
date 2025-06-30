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
    switch (descricao) {
      case "video":
        return VIDEO;
      case "pdf":
        return PDF;
      case "imagem":
        return IMAGEM;
      case "audio":
        return AUDIO;
      case "quiz":
        return QUIZ;
      case "slide":
        return SLIDE;

      default:
        return null;
    }
  }
}