/*
 * 4.
 * Escreva um programa que apresente quatro opções:
 * (a) consulta saldo, (b) saque, (c) depósito e (d) sair.
 * O saldo deve iniciar em R$ 0,00. A cada saque ou depósito o valor do saldo deve ser atualizado.
 */

import java.util.Scanner;

public class Saldo {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    float saldo = 0;
    while (true) {
      System.out.println("(a) consulta saldo\n(b) saque\n(c) depósito\n(d) sair");
      String input = in.next();

      if (input.equals("a")) {
        System.out.printf("Saldo atual: R$ %.2f\n", saldo);
      } else if (input.equals("b")) {
        System.out.print("Valor do saque: R$ ");
        float saque = in.nextFloat();
        if (saque > saldo)
          System.out.println("Saldo insuficiente");
        else
          saldo -= saque;
        System.out.printf("Saldo atual: R$ %.2f\n", saldo);
      } else if (input.equals("c")) {
        System.out.print("Valor do depósito: R$ ");
        float deposito = in.nextFloat();
        saldo += deposito;
        System.out.printf("Saldo atual: R$ %.2f\n", saldo);
      } else {
        break;
      }
    }

    in.close();
  }
}
