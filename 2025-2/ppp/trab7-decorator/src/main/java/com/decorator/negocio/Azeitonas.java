package com.decorator.negocio;

public class Azeitonas extends IngredienteDecorator {

  public Azeitonas(Pizza pizza) {
    super(pizza);
  }

  @Override
  public String getDescricao() {
    return pizza.getDescricao() + " + Azeitonas";
  }

  @Override
  public double getPreco() {
    return pizza.getPreco() + 3.00;
  }
}
