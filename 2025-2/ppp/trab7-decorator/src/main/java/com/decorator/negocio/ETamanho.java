package com.decorator.negocio;

public enum ETamanho {
  PEQUENA("Pequena", 8.00),
  MEDIA("Média", 10.00),
  GRANDE("Grande", 12.00);

  private String descricao;
  private double preco;

  ETamanho(String descricao, double preco) {
    this.descricao = descricao;
    this.preco = preco;
  }

  public String getDescricao() {
    return descricao;
  }

  public double getPreco() {
    return preco;
  }
}