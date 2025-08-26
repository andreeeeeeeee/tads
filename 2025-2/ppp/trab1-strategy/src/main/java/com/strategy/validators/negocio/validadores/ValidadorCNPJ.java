package com.strategy.validators.negocio.validadores;

import com.strategy.validators.negocio.interfaces.ValidadorStrategy;

public class ValidadorCNPJ implements ValidadorStrategy {
  private static final String[] INVALIDOS = {
      "00000000000000", "11111111111111", "22222222222222", "33333333333333", "44444444444444",
      "55555555555555", "66666666666666", "77777777777777", "88888888888888", "99999999999999"
  };

  @Override
  public boolean validar(String entrada) {
    if (entrada == null)
      return false;

    String cnpj = entrada.replaceAll("[\\.\\-/]", "");

    if (!cnpj.matches("\\d{14}"))
      return false;

    for (String inv : INVALIDOS)
      if (cnpj.equals(inv))
        return false;

    String digitos = cnpj.substring(12);

    for (int i = 0; i < 2; i++) {
      int tamanho = 12 + i;
      String numeros = cnpj.substring(0, tamanho);
      int soma = 0;
      int pos = tamanho - 7;

      for (int j = tamanho; j >= 1; j--) {
        soma += Character.getNumericValue(numeros.charAt(tamanho - j)) * pos--;
        if (pos < 2)
          pos = 9;
      }

      int resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
      if (resultado != Character.getNumericValue(digitos.charAt(i)))
        return false;
    }
    return true;
  }
}
