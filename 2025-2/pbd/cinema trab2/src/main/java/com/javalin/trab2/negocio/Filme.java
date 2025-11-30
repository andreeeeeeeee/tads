package com.javalin.trab2.negocio;

public class Filme {
  private int id;
  private String titulo;
  private String sinopse;
  private String classificacaoEtaria;
  private int duracaoMinutos;

  public Filme() {
  }

  public Filme(String titulo, String sinopse) {
    this.titulo = titulo;
    this.sinopse = sinopse;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getSinopse() {
    return sinopse;
  }

  public void setSinopse(String sinopse) {
    this.sinopse = sinopse;
  }

  public String getClassificacaoEtaria() {
    return classificacaoEtaria;
  }

  public void setClassificacaoEtaria(String classificacaoEtaria) {
    this.classificacaoEtaria = classificacaoEtaria;
  }

  public int getDuracaoMinutos() {
    return duracaoMinutos;
  }

  public void setDuracaoMinutos(int duracaoMinutos) {
    this.duracaoMinutos = duracaoMinutos;
  }

  @Override
  public String toString() {
    return "Filme{" +
        "id=" + id +
        ", titulo='" + titulo + '\'' +
        ", sinopse='" + sinopse + '\'' +
        ", classificacaoEtaria='" + classificacaoEtaria + '\'' +
        ", duracaoMinutos=" + duracaoMinutos +
        '}';
  }
}
