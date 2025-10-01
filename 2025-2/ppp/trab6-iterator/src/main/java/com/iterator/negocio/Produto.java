package com.iterator.negocio;

public class Produto {
  private String nome;
  private String categoria;
  private double preco;
  private boolean disponivel;

  public Produto(String nome, String categoria, double preco, boolean disponivel) {
    this.nome = nome;
    this.categoria = categoria;
    this.preco = preco;
    this.disponivel = disponivel;
  }

  @Override
  public String toString() {
    return String.format("%s - %s - R$%.2f - %s",
        nome, categoria, preco, disponivel ? "Disponível" : "Não disponível");
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public double getPreco() {
    return preco;
  }

  public void setPreco(double preco) {
    this.preco = preco;
  }

  public boolean isDisponivel() {
    return disponivel;
  }

  public void setDisponivel(boolean disponivel) {
    this.disponivel = disponivel;
  }
}