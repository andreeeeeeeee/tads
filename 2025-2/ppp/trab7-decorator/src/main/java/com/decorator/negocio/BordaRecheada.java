package com.decorator.negocio;

public class BordaRecheada extends IngredienteDecorator {

  public BordaRecheada(Pizza pizza) {
    super(pizza);
  }

  @Override
  public String getDescricao() {
    return pizza.getDescricao() + " + Borda Recheada (Catupiry)";
  }

  @Override
  public double getPreco() {
    return pizza.getPreco() + 8.00;
  }
}
