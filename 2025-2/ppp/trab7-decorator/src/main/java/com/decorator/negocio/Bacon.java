package com.decorator.negocio;

public class Bacon extends IngredienteDecorator {

  public Bacon(Pizza pizza) {
    super(pizza);
  }

  @Override
  public String getDescricao() {
    return pizza.getDescricao() + " + Bacon";
  }

  @Override
  public double getPreco() {
    return pizza.getPreco() + 6.00;
  }
}
