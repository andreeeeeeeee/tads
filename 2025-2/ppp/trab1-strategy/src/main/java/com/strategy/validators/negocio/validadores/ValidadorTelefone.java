package com.strategy.validators.negocio.validadores;

import com.strategy.validators.negocio.interfaces.ValidadorStrategy;

public class ValidadorTelefone implements ValidadorStrategy {
  @Override
  public boolean validar(String entrada) {
    return entrada != null && entrada.matches("\\(\\d{2}\\) \\d{4,5}-\\d{4}");
  }
}
