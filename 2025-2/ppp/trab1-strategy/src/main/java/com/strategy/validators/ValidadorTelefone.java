package com.strategy.validators;

public class ValidadorTelefone implements ValidadorStrategy {
  @Override
  public boolean validar(String entrada) {
    return entrada != null && entrada.matches("\\(\\d{2}\\) \\d{4,5}-\\d{4}");
  }
}
