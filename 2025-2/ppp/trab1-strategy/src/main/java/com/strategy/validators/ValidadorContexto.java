package com.strategy.validators;

public class ValidadorContexto {
  private ValidadorStrategy strategy;

  public void setStrategy(ValidadorStrategy strategy) {
    this.strategy = strategy;
  }

  public boolean validar(String entrada) {
    if (strategy == null)
      throw new IllegalStateException("Strategy não definida");
    return strategy.validar(entrada);
  }
}
