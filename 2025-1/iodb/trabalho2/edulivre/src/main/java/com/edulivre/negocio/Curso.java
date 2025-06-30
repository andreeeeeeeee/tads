package com.edulivre.negocio;

import java.sql.Date;
import java.util.UUID;

import org.json.JSONObject;

public class Curso {
  private UUID id;
  private String titulo;
  private String descricao;
  private Date dataCriacao;
  private JSONObject avaliacao;

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Date getDataCriacao() {
    return this.dataCriacao;
  }

  public void setDataCriacao(Date dataCriacao) {
    this.dataCriacao = dataCriacao;
  }

  public JSONObject getAvaliacao() {
    return avaliacao;
  }

  public void setAvaliacao(JSONObject avaliacao) {
    this.avaliacao = avaliacao;
  }
}
