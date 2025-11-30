package com.javalin.trab1.negocio;

public class RelatorioClienteFrequente {
  private String cpf;
  private String nome;
  private int quantidadeIngressos;
  private double totalGasto;
  private double valorMedioIngresso;

  public RelatorioClienteFrequente() {
  }

  public RelatorioClienteFrequente(String cpf, String nome, int quantidadeIngressos,
      double totalGasto, double valorMedioIngresso) {
    this.cpf = cpf;
    this.nome = nome;
    this.quantidadeIngressos = quantidadeIngressos;
    this.totalGasto = totalGasto;
    this.valorMedioIngresso = valorMedioIngresso;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public int getQuantidadeIngressos() {
    return quantidadeIngressos;
  }

  public void setQuantidadeIngressos(int quantidadeIngressos) {
    this.quantidadeIngressos = quantidadeIngressos;
  }

  public double getTotalGasto() {
    return totalGasto;
  }

  public void setTotalGasto(double totalGasto) {
    this.totalGasto = totalGasto;
  }

  public double getValorMedioIngresso() {
    return valorMedioIngresso;
  }

  public void setValorMedioIngresso(double valorMedioIngresso) {
    this.valorMedioIngresso = valorMedioIngresso;
  }

  @Override
  public String toString() {
    return "RelatorioClienteFrequente{" +
        "cpf='" + cpf + '\'' +
        ", nome='" + nome + '\'' +
        ", quantidadeIngressos=" + quantidadeIngressos +
        ", totalGasto=" + totalGasto +
        ", valorMedioIngresso=" + valorMedioIngresso +
        '}';
  }
}
