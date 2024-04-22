/*
 * 6.
 * MOSTRE OS 15 PRIMEIROS TERMOS DA SEQUENCIA DE FIBONACCI
 */

import java.util.Scanner;

public class Fibonacci {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int i = 0, soma = 1, ultimo = 0;

    while (i < 15) {
      System.out.println(soma);
      if (i > 0) {
        soma += ultimo;
      }

      ultimo = soma - ultimo;
      i++;
    }

    in.close();
  }
}
