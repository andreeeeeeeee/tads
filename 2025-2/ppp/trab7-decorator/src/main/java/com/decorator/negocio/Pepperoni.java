package com.decorator.negocio;

public class Pepperoni extends IngredienteDecorator {

  public Pepperoni(Pizza pizza) {
    super(pizza);
  }

  @Override
  public String getDescricao() {
    return pizza.getDescricao() + " + Pepperoni";
  }

  @Override
  public double getPreco() {
    return pizza.getPreco() + 7.00;
  }
}
