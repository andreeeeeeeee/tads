/*
 * 3.
 * MOSTRE TODOS OS DIVISORES DE UM NUMERO
 */

import java.util.Scanner;

public class Divisores {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int divisor = 1, numero;

    System.out.print("Digite um número: ");
    numero = in.nextInt();

    while (divisor <= numero) {
      if (numero % divisor == 0)
        System.out.println(divisor);

      divisor++;
    }

    in.close();
  }
}
