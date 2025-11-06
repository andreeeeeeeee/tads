package com.decorator.negocio;

public class PizzaCalabresa extends Pizza {

  public PizzaCalabresa() {
    this.nome = "Pizza Calabresa";
    this.tamanho = ETamanho.MEDIA;
  }

  public PizzaCalabresa(ETamanho tamanho) {
    this.nome = "Pizza Calabresa";
    this.tamanho = tamanho;
  }

  @Override
  public double getPreco() {
    return 28.00 + this.tamanho.getPreco();
  }
}
