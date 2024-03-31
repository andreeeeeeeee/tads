/*
 * 5. Faça um programa que leia o valor de 3 notas disponíveis num caixa eletrônico em ordem
 * crescente. Depois disso solicite ao usuário o valor que deseja sacar, o programa deve
 * retornar a quantidade de cada nota a ser entregue ao cliente.
 */

import java.util.Scanner;

public class Caixa {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int n1, n2, n3, saque, qtd1, qtd2, qtd3;

    System.out.println("Digite os valores das cédulas disponíveis no caixa, em ordem crescente:");
    System.out.print("R$ ");
    n1 = in.nextInt();
    System.out.print("R$ ");
    n2 = in.nextInt();
    System.out.print("R$ ");
    n3 = in.nextInt();

    System.out.println("Digite o valor que deseja sacar:");
    System.out.print("R$ ");
    saque = in.nextInt();

    qtd3 = saque / n3;
    saque = saque % n3;
    qtd2 = saque / n2;
    saque = saque % n2;
    qtd1 = saque / n1;
    saque = saque % n1;

    if (saque == 0) {
      System.out.printf("%d notas de %d, %d notas de %d e %d notas de %d\n", qtd1, n1, qtd2, n2, qtd3, n3);
    } else {
      System.out.println("Valor de saque indisponível.");
    }

    in.close();
  }
}
