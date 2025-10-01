package com.iterator.negocio;

public class EstoqueFiltradoIterator implements Iterator<Produto> {
  private Estoque estoque;
  private int posicaoAtual;
  private Produto proximoProduto;
  private boolean proximoEncontrado;

  public EstoqueFiltradoIterator(Estoque estoque) {
    this.estoque = estoque;
    this.posicaoAtual = 0;
    this.proximoEncontrado = false;
    encontrarProximo();
  }

  @Override
  public boolean hasNext() {
    return proximoEncontrado;
  }

  @Override
  public Produto next() {
    if (!hasNext()) {
      throw new IllegalStateException("Não há mais elementos disponíveis para iterar");
    }

    Produto produtoAtual = proximoProduto;
    encontrarProximo();
    return produtoAtual;
  }

  private void encontrarProximo() {
    proximoEncontrado = false;

    while (posicaoAtual < estoque.getProdutos().size()) {
      Produto produto = estoque.getProdutos().get(posicaoAtual);
      posicaoAtual++;

      if (produto.isDisponivel()) {
        proximoProduto = produto;
        proximoEncontrado = true;
        break;
      }
    }
  }
}