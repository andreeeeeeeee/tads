package com.javalin.trab1.negocio;

public class Poltrona {
  private int id;
  private int salaId;
  private String fileira;
  private int numero;
  private boolean disponivel;

  public Poltrona() {
  }

  public Poltrona(int salaId, String fileira, int numero) {
    this.salaId = salaId;
    this.fileira = fileira;
    this.numero = numero;
    this.disponivel = true;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getSalaId() {
    return salaId;
  }

  public void setSalaId(int salaId) {
    this.salaId = salaId;
  }

  public String getFileira() {
    return fileira;
  }

  public void setFileira(String fileira) {
    this.fileira = fileira;
  }

  public int getNumero() {
    return numero;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }

  public boolean isDisponivel() {
    return disponivel;
  }

  public void setDisponivel(boolean disponivel) {
    this.disponivel = disponivel;
  }

  @Override
  public String toString() {
    return "Poltrona{" +
        "id=" + id +
        ", salaId=" + salaId +
        ", fileira='" + fileira + '\'' +
        ", numero=" + numero +
        ", disponivel=" + disponivel +
        '}';
  }
}
