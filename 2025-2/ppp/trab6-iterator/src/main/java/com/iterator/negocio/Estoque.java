package com.iterator.negocio;

import java.util.ArrayList;
import java.util.List;

public class Estoque {
  private List<Produto> produtos;

  public Estoque() {
    this.produtos = new ArrayList<>();

    adicionarProduto(new Produto("Smartphone Samsung", "Eletrônicos", 1200.00, true));
    adicionarProduto(new Produto("Fone Bluetooth", "Eletrônicos", 200.00, true));
    adicionarProduto(new Produto("Carregador USB-C", "Acessórios", 30.00, false));
    adicionarProduto(new Produto("Capa Protetora", "Acessórios", 25.00, true));
    adicionarProduto(new Produto("Tablet iPad", "Eletrônicos", 2000.00, true));
  }

  public void adicionarProduto(Produto produto) {
    produtos.add(produto);
  }

  public List<Produto> getProdutos() {
    return produtos;
  }

  public Iterator<Produto> createIterator() {
    return new EstoqueIterator(this);
  }

  public Iterator<Produto> createReverseIterator() {
    return new EstoqueReversoIterator(this);
  }

  public Iterator<Produto> createFilteredIterator() {
    return new EstoqueFiltradoIterator(this);
  }
}