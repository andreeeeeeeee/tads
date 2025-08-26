package com.strategy.validators;

public class ValidadorCPF implements ValidadorStrategy {
  private static final String[] INVALIDOS = {
      "00000000000", "11111111111", "22222222222", "33333333333", "44444444444",
      "55555555555", "66666666666", "77777777777", "88888888888", "99999999999"
  };

  @Override
  public boolean validar(String entrada) {
    if (entrada == null)
      return false;

    String cpf = entrada.replaceAll("[\\.\\-]", "");

    if (!cpf.matches("\\d{11}"))
      return false;

    for (String inv : INVALIDOS)
      if (cpf.equals(inv))
        return false;

    for (int i = 9; i <= 10; i++) {
      int soma = 0;
      for (int j = 1; j <= i; j++) {
        soma += Character.getNumericValue(cpf.charAt(j - 1)) * (i + 2 - j);
      }
      int resto = soma % 11 < 2 ? 0 : 11 - soma % 11;
      if (resto != Character.getNumericValue(cpf.charAt(i)))
        return false;
    }
    return true;
  }
}
