package com.edulivre.negocio;

public enum Tipo {
  VIDEO("Vídeo"),
  PDF("PDF"),
  IMAGEM("Imagem"),
  AUDIO("Áudio"),
  QUIZ("Quiz"),
  SLIDE("Slide");

  private final String descricao;

  Tipo(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return this.descricao;
  }
}