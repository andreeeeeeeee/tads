package com.decorator.negocio;

public class Cogumelos extends IngredienteDecorator {

  public Cogumelos(Pizza pizza) {
    super(pizza);
  }

  @Override
  public String getDescricao() {
    return pizza.getDescricao() + " + Cogumelos";
  }

  @Override
  public double getPreco() {
    return pizza.getPreco() + 4.50;
  }
}
