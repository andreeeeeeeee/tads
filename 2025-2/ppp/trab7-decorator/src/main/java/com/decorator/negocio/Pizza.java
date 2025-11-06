package com.decorator.negocio;

public abstract class Pizza {
  protected String nome = "Pizza Desconhecida";
  protected ETamanho tamanho = ETamanho.MEDIA;

  public String getDescricao() {
    return nome + " (" + tamanho.getDescricao() + ")";
  }

  public abstract double getPreco();
}
