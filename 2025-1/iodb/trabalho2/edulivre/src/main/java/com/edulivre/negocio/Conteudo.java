package com.edulivre.negocio;

import java.util.UUID;

public class Conteudo {
  private int id;
  private UUID cursoID;
  private String titulo;
  private String descricao;
  private Tipo tipo;
  private byte[] arquivo;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public UUID getCursoID() {
    return cursoID;
  }

  public void setCursoID(UUID cursoID) {
    this.cursoID = cursoID;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Tipo getTipo() {
    return tipo;
  }

  public void setTipo(Tipo tipo) {
    this.tipo = tipo;
  }

  public byte[] getArquivo() {
    return arquivo;
  }

  public void setArquivo(byte[] arquivo) {
    this.arquivo = arquivo;
  }
}
