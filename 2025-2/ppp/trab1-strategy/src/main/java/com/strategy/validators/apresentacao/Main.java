package com.strategy.validators.apresentacao;

import com.strategy.validators.ValidadorContexto;
import com.strategy.validators.negocio.validadores.ValidadorCNPJ;
import com.strategy.validators.negocio.validadores.ValidadorCPF;
import com.strategy.validators.negocio.validadores.ValidadorEmail;
import com.strategy.validators.negocio.validadores.ValidadorTelefone;

public class Main {
  public static void main(String[] args) {
    ValidadorContexto contexto = new ValidadorContexto();

    String cpf = "123.456.789-09";
    String cnpj = "12.345.678/0001-95";
    String email = "teste@exemplo.com";
    String telefone = "(11) 91234-5678";

    contexto.setStrategy(new ValidadorCPF());
    System.out.println("CPF válido? " + contexto.validar(cpf));

    contexto.setStrategy(new ValidadorCNPJ());
    System.out.println("CNPJ válido? " + contexto.validar(cnpj));

    contexto.setStrategy(new ValidadorEmail());
    System.out.println("Email válido? " + contexto.validar(email));

    contexto.setStrategy(new ValidadorTelefone());
    System.out.println("Telefone válido? " + contexto.validar(telefone));
  }
}