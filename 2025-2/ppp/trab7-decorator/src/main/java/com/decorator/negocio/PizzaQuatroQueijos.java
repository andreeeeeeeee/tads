package com.decorator.negocio;

public class PizzaQuatroQueijos extends Pizza {

  public PizzaQuatroQueijos() {
    this.nome = "Pizza Quatro Queijos";
    this.tamanho = ETamanho.MEDIA;
  }

  public PizzaQuatroQueijos(ETamanho tamanho) {
    this.nome = "Pizza Quatro Queijos";
    this.tamanho = tamanho;
  }

  @Override
  public double getPreco() {
    return 32.00 + this.tamanho.getPreco();
  }
}
