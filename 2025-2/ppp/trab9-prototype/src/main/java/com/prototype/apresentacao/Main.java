package com.prototype.apresentacao;

import com.prototype.negocio.Cardapio;
import com.prototype.negocio.CardapioRegistry;
import com.prototype.negocio.ECategoria;
import com.prototype.negocio.ItemCardapio;

public class Main {

  public static void main(String[] args) {
    try {
      CardapioRegistry registry = CardapioRegistry.getInstance();

      Cardapio executivo = new Cardapio("Cardápio Executivo", "Executivo", "Pratos rápidos e saborosos.");
      executivo.adicionarItem(
          new ItemCardapio("Bife Acebolado", "Bife com cebola e arroz.", 30, ECategoria.PRATO_PRINCIPAL));
      executivo.adicionarItem(
          new ItemCardapio("Pudim", "Pudim de leite condensado.", 10, ECategoria.SOBREMESA));

      Cardapio vegetariano = new Cardapio("Cardápio Vegetariano", "Vegetariano", "Opções saudáveis e sem carne.");
      vegetariano.adicionarItem(
          new ItemCardapio("Salada Verde", "Mix de folhas com molho especial.", 20, ECategoria.ENTRADA));
      vegetariano.adicionarItem(
          new ItemCardapio("Risoto de Cogumelos", "Risoto com cogumelos frescos.", 20, ECategoria.PRATO_PRINCIPAL));

      registry.registrar("executivo", executivo);
      registry.registrar("vegetariano", vegetariano);

      registry.listarPrototipos();

      Cardapio novoExecutivo = registry.criar("executivo");
      novoExecutivo.setNome("Cardápio Executivo - Edição Especial");
      novoExecutivo.adicionarItem(
          new ItemCardapio("Suco de Laranja", "Suco natural.", 5, ECategoria.BEBIDA));

      Cardapio novoVegetariano = registry.criar("vegetariano");
      novoVegetariano.setNome("Cardápio Vegetariano - Edição Especial");
      novoVegetariano.adicionarItem(
          new ItemCardapio("Torta de Maçã", "Torta caseira.", 5, ECategoria.SOBREMESA));

      executivo.exibir();
      vegetariano.exibir();
      novoExecutivo.exibir();
      novoVegetariano.exibir();

    } catch (CloneNotSupportedException e) {
      System.err.println("Erro ao clonar cardápio: " + e.getMessage());
    }
  }
}
