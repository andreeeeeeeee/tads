package com.trab10.builder.apresentacao;

import com.trab10.builder.negocio.Pessoa;

public class Main {
  public static void main(String[] args) {
    Pessoa pessoa1 = new Pessoa.Builder()
        .nome("João")
        .sobrenome("Silva")
        .email("joao.silva@example.com")
        .telefone("(11) 99999-9999")
        .cargo("Desenvolvedor")
        .build();

    System.out.println(pessoa1);
    System.out.println();

    Pessoa pessoa2 = new Pessoa.Builder()
        .nome("Maria")
        .sobrenome("Santos")
        .email("maria.santos@example.com")
        .telefone("(21) 98888-8888")
        .endereco("Rua das Flores, 123")
        .cidade("Rio de Janeiro")
        .estado("RJ")
        .dataNascimento("15/03/1990")
        .cargo("Gerente de Projetos")
        .documento("123.456.789-00")
        .build();

    System.out.println(pessoa2);
    System.out.println();

    Pessoa pessoaMinima = new Pessoa.Builder()
        .nome("Ana")
        .email("ana@example.com")
        .build();

    System.out.println(pessoaMinima);
    System.out.println();

    try {
      new Pessoa.Builder()
          .email("sem.nome@example.com")
          .build();
    } catch (IllegalArgumentException e) {
      System.out.println("Erro: " + e.getMessage());
    }
  }
}