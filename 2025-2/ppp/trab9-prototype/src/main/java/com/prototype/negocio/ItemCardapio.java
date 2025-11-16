package com.prototype.negocio;

public class ItemCardapio implements Cloneable {
  private String nome;
  private String descricao;
  private double preco;
  private ECategoria categoria;

  public ItemCardapio(String nome, String descricao, double preco, ECategoria categoria) {
    this.nome = nome;
    this.descricao = descricao;
    this.preco = preco;
    this.categoria = categoria;
  }

  @Override
  public ItemCardapio clone() throws CloneNotSupportedException, AssertionError {
    try {
      return (ItemCardapio) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError("Erro ao clonar ItemCardapio", e);
    }
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public double getPreco() {
    return preco;
  }

  public void setPreco(double preco) {
    this.preco = preco;
  }

  public ECategoria getCategoria() {
    return categoria;
  }

  public void setCategoria(ECategoria categoria) {
    this.categoria = categoria;
  }

  @Override
  public String toString() {
    return String.format("  %s - R$ %.2f\n    %s", nome, preco, descricao);
  }
}
