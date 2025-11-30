package com.javalin.trab2.negocio;

import java.time.LocalDateTime;

public class Ingresso {
  private int id;
  private String cpf;
  private int sessaoId;
  private double valor;
  private int poltronaId;
  private LocalDateTime dataCompra;

  public Ingresso() {
  }

  public Ingresso(String cpf, int sessaoId, double valor, int poltronaId) {
    this.cpf = cpf;
    this.sessaoId = sessaoId;
    this.valor = valor;
    this.poltronaId = poltronaId;
    this.dataCompra = LocalDateTime.now();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public int getSessaoId() {
    return sessaoId;
  }

  public void setSessaoId(int sessaoId) {
    this.sessaoId = sessaoId;
  }

  public double getValor() {
    return valor;
  }

  public void setValor(double valor) {
    this.valor = valor;
  }

  public int getPoltronaId() {
    return poltronaId;
  }

  public void setPoltronaId(int poltronaId) {
    this.poltronaId = poltronaId;
  }

  public LocalDateTime getDataCompra() {
    return dataCompra;
  }

  public void setDataCompra(LocalDateTime dataCompra) {
    this.dataCompra = dataCompra;
  }

  @Override
  public String toString() {
    return "Ingresso{" +
        "id=" + id +
        ", cpf='" + cpf + '\'' +
        ", sessaoId=" + sessaoId +
        ", valor=" + valor +
        ", poltronaId=" + poltronaId +
        ", dataCompra=" + dataCompra +
        '}';
  }
}
