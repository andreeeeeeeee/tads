package com.javalin.trab1.negocio;

import java.time.LocalDateTime;

public class Sessao {
  private int id;
  private int filmeId;
  private int salaId;
  private LocalDateTime horario;
  private double valor;

  public Sessao() {
  }

  public Sessao(int filmeId, int salaId, LocalDateTime horario, double valor) {
    this.filmeId = filmeId;
    this.salaId = salaId;
    this.horario = horario;
    this.valor = valor;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getFilmeId() {
    return filmeId;
  }

  public void setFilmeId(int filmeId) {
    this.filmeId = filmeId;
  }

  public int getSalaId() {
    return salaId;
  }

  public void setSalaId(int salaId) {
    this.salaId = salaId;
  }

  public LocalDateTime getHorario() {
    return horario;
  }

  public void setHorario(LocalDateTime horario) {
    this.horario = horario;
  }

  public double getValor() {
    return valor;
  }

  public void setValor(double valor) {
    this.valor = valor;
  }

  @Override
  public String toString() {
    return "Sessao{" +
        "id=" + id +
        ", filmeId=" + filmeId +
        ", salaId=" + salaId +
        ", horario=" + horario +
        ", valor=" + valor +
        '}';
  }
}
