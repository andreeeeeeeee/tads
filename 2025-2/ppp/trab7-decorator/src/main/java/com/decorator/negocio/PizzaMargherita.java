package com.decorator.negocio;

public class PizzaMargherita extends Pizza {

  public PizzaMargherita() {
    this.nome = "Pizza Margherita";
    this.tamanho = ETamanho.MEDIA;
  }

  public PizzaMargherita(ETamanho tamanho) {
    this.nome = "Pizza Margherita";
    this.tamanho = tamanho;
  }

  @Override
  public double getPreco() {
    return 25.00 + this.tamanho.getPreco();
  }
}
