package com.prototype.negocio;

import java.util.ArrayList;
import java.util.List;

public class Cardapio implements Cloneable {
  private String nome;
  private String tipo;
  private List<ItemCardapio> itens;
  private String descricaoGeral;

  public Cardapio(String nome, String tipo, String descricaoGeral) {
    this.nome = nome;
    this.tipo = tipo;
    this.descricaoGeral = descricaoGeral;
    this.itens = new ArrayList<>();
  }

  @Override
  public Cardapio clone() throws CloneNotSupportedException, AssertionError {
    try {
      Cardapio clone = (Cardapio) super.clone();
      clone.itens = new ArrayList<>();
      for (ItemCardapio item : this.itens) {
        clone.itens.add(item.clone());
      }
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError("Erro ao clonar Cardapio", e);
    }
  }

  public void adicionarItem(ItemCardapio item) {
    this.itens.add(item);
  }

  public void removerItem(ItemCardapio item) {
    this.itens.remove(item);
  }

  public double calcularValorTotal() {
    return itens.stream().mapToDouble(ItemCardapio::getPreco).sum();
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public List<ItemCardapio> getItens() {
    return itens;
  }

  public void setItens(List<ItemCardapio> itens) {
    this.itens = itens;
  }

  public String getDescricaoGeral() {
    return descricaoGeral;
  }

  public void setDescricaoGeral(String descricaoGeral) {
    this.descricaoGeral = descricaoGeral;
  }

  public void exibir() {
    System.out.println(nome + " - " + tipo);
    System.out.println(descricaoGeral);

    ECategoria categoriaAtual = null;
    for (ItemCardapio item : itens) {
      if (!item.getCategoria().equals(categoriaAtual)) {
        categoriaAtual = item.getCategoria();
        System.out.println("\n" + categoriaAtual.getDescricao() + ":");
      }
      System.out.println(item);
    }

    System.out.println();
    System.out.printf("Valor Total: R$ %.2f\n", calcularValorTotal());
    System.out.println();
    System.out.println();
  }
}
