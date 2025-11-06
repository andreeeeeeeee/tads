package com.decorator.negocio;

public abstract class IngredienteDecorator extends Pizza {
  protected Pizza pizza;

  public IngredienteDecorator(Pizza pizza) {
    this.pizza = pizza;
  }

  @Override
  public abstract String getDescricao();
}
