package com.decorator.negocio;

public class QueijoExtra extends IngredienteDecorator {

  public QueijoExtra(Pizza pizza) {
    super(pizza);
  }

  @Override
  public String getDescricao() {
    return pizza.getDescricao() + " + Queijo Extra";
  }

  @Override
  public double getPreco() {
    return pizza.getPreco() + 5.00;
  }
}
