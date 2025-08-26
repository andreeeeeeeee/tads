package com.strategy.validators;

public class ValidadorEmail implements ValidadorStrategy {
  @Override
  public boolean validar(String entrada) {
    return entrada != null && entrada.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
  }
}
