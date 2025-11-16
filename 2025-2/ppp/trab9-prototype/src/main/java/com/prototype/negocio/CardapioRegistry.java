package com.prototype.negocio;

import java.util.HashMap;
import java.util.Map;

public class CardapioRegistry {
  private static CardapioRegistry INSTANCE;

  private final Map<String, Cardapio> prototipos;

  private CardapioRegistry() {
    this.prototipos = new HashMap<>();
  }

  public static synchronized CardapioRegistry getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new CardapioRegistry();
    }
    return INSTANCE;
  }

  public void registrar(String chave, Cardapio prototipo) {
    prototipos.put(chave, prototipo);
  }

  public Cardapio criar(String chave) throws CloneNotSupportedException {
    Cardapio prototipo = prototipos.get(chave);
    return (prototipo != null) ? prototipo.clone() : null;
  }

  public void remover(String chave) {
    prototipos.remove(chave);
  }

  public boolean existe(String chave) {
    return prototipos.containsKey(chave);
  }

  public void listarPrototipos() {
    System.out.println("\nProtótipos de Cardápios Registrados:");
    if (prototipos.isEmpty()) {
      System.out.println("Nenhum protótipo registrado.");
    } else {
      prototipos.forEach((chave, cardapio) -> {
        System.out.printf("[%s] - %s (%s)\n", chave, cardapio.getNome(), cardapio.getTipo());
      });
    }
    System.out.println();
  }

  public int quantidadePrototipos() {
    return prototipos.size();
  }

  public void limparTodos() {
    prototipos.clear();
  }
}
