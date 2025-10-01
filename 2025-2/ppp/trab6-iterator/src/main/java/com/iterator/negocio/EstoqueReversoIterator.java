package com.iterator.negocio;

public class EstoqueReversoIterator implements Iterator<Produto> {
  private Estoque estoque;
  private int posicaoAtual;

  public EstoqueReversoIterator(Estoque estoque) {
    this.estoque = estoque;
    this.posicaoAtual = estoque.getProdutos().size() - 1;
  }

  @Override
  public boolean hasNext() {
    return posicaoAtual >= 0;
  }

  @Override
  public Produto next() {
    if (!hasNext()) {
      throw new IllegalStateException("Não há mais elementos para iterar");
    }

    Produto produto = estoque.getProdutos().get(posicaoAtual);
    posicaoAtual--;
    return produto;
  }
}