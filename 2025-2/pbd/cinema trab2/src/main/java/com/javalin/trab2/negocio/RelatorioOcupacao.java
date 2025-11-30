package com.javalin.trab2.negocio;

public class RelatorioOcupacao {
  private int sessaoId;
  private String tituloFilme;
  private int salaId;
  private int capacidade;
  private int ocupados;
  private double percentual;

  public RelatorioOcupacao() {
  }

  public int getSessaoId() {
    return sessaoId;
  }

  public void setSessaoId(int sessaoId) {
    this.sessaoId = sessaoId;
  }

  public String getTituloFilme() {
    return tituloFilme;
  }

  public void setTituloFilme(String tituloFilme) {
    this.tituloFilme = tituloFilme;
  }

  public int getSalaId() {
    return salaId;
  }

  public void setSalaId(int salaId) {
    this.salaId = salaId;
  }

  public int getCapacidade() {
    return capacidade;
  }

  public void setCapacidade(int capacidade) {
    this.capacidade = capacidade;
  }

  public int getOcupados() {
    return ocupados;
  }

  public void setOcupados(int ocupados) {
    this.ocupados = ocupados;
  }

  public double getPercentual() {
    return percentual;
  }

  public void setPercentual(double percentual) {
    this.percentual = percentual;
  }

  @Override
  public String toString() {
    return "RelatorioOcupacao{" +
        "sessaoId=" + sessaoId +
        ", tituloFilme='" + tituloFilme + '\'' +
        ", salaId=" + salaId +
        ", capacidade=" + capacidade +
        ", ocupados=" + ocupados +
        ", percentual=" + percentual +
        '}';
  }
}
