package com.iterator.negocio;

public class EstoqueIterator implements Iterator<Produto> {
  private Estoque estoque;
  private int posicaoAtual;

  public EstoqueIterator(Estoque estoque) {
    this.estoque = estoque;
    this.posicaoAtual = 0;
  }

  @Override
  public boolean hasNext() {
    return posicaoAtual < estoque.getProdutos().size();
  }

  @Override
  public Produto next() {
    if (!hasNext()) {
      throw new IllegalStateException("Não há mais elementos para iterar");
    }

    Produto produto = estoque.getProdutos().get(posicaoAtual);
    posicaoAtual++;
    return produto;
  }
}