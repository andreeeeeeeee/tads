package com.decorator.apresentacao;

import com.decorator.negocio.*;

public class Main {
  public static void main(String[] args) {
    System.out.println("=== PIZZARIA DECORATOR === \n");

    Pizza pedido1 = new PizzaMargherita(ETamanho.GRANDE);
    System.out.println("PEDIDO 1:");
    System.out.println("   " + pedido1.getDescricao());
    System.out.println("   Preço: R$ " + String.format("%.2f", pedido1.getPreco()));
    System.out.println();

    Pizza pedido2 = new PizzaCalabresa(ETamanho.MEDIA);
    pedido2 = new QueijoExtra(pedido2);
    System.out.println("PEDIDO 2:");
    System.out.println("   " + pedido2.getDescricao());
    System.out.println("   Preço: R$ " + String.format("%.2f", pedido2.getPreco()));
    System.out.println();

    Pizza pedido3 = new PizzaMargherita();
    pedido3 = new Bacon(pedido3);
    pedido3 = new Cogumelos(pedido3);
    pedido3 = new QueijoExtra(pedido3);
    pedido3 = new Azeitonas(pedido3);
    pedido3 = new BordaRecheada(pedido3);
    System.out.println("PEDIDO 3 (Pizza Especial):");
    System.out.println("   " + pedido3.getDescricao());
    System.out.println("   Preço: R$ " + String.format("%.2f", pedido3.getPreco()));
    System.out.println();

    Pizza pedido4 = new PizzaQuatroQueijos(ETamanho.PEQUENA);
    pedido4 = new Pepperoni(pedido4);
    pedido4 = new Bacon(pedido4);
    pedido4 = new BordaRecheada(pedido4);
    System.out.println("PEDIDO 4 (Premium):");
    System.out.println("   " + pedido4.getDescricao());
    System.out.println("   Preço: R$ " + String.format("%.2f", pedido4.getPreco()));
    System.out.println();

    Pizza pedido5 = new PizzaCalabresa();
    pedido5 = new QueijoExtra(pedido5);
    pedido5 = new QueijoExtra(pedido5);
    pedido5 = new Cogumelos(pedido5);
    pedido5 = new Pepperoni(pedido5);
    System.out.println("PEDIDO 5 (Calabresa Completa):");
    System.out.println("   " + pedido5.getDescricao());
    System.out.println("   Preço: R$ " + String.format("%.2f", pedido5.getPreco()));
    System.out.println();
  }
}