package com.iterator.apresentacao;

import com.iterator.negocio.*;

public class Main {

  private static void percorrerProdutos(Iterator<Produto> iterator) {
    if (!iterator.hasNext()) {
      System.out.println("Nenhum produto encontrado.");
      return;
    }

    for (int i = 1; iterator.hasNext(); i++) {
      System.out.println(i + ". " + iterator.next());
    }
  }

  public static void main(String[] args) {
    Estoque estoque = new Estoque();

    System.out.println();
    System.out.println("PRODUTOS NO ESTOQUE (ordem normal)");
    Iterator<Produto> iteratorLista = estoque.createIterator();
    percorrerProdutos(iteratorLista);

    System.out.println();
    System.out.println("PRODUTOS NO ESTOQUE (ordem reversa)");
    Iterator<Produto> iteratorReverso = estoque.createReverseIterator();
    percorrerProdutos(iteratorReverso);

    System.out.println();
    System.out.println("PRODUTOS DISPONÍVEIS (filtrado)");
    Iterator<Produto> iteratorFiltrado = estoque.createFilteredIterator();
    percorrerProdutos(iteratorFiltrado);
  }
}