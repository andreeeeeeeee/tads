/*
 * 4.
 * INDIQUE SE UM NUMERO É PRIMO.
 */

import java.util.Scanner;

public class Primo {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int divisor = 1, numero, divisores = 0;

    System.out.print("Digite um número: ");
    numero = in.nextInt();

    while (divisor <= numero) {
      if (numero % divisor == 0)
        divisores++;

      divisor++;
    }

    if (divisores > 2)
      System.out.println("Número composto");
    else
      System.out.println("Número primo");

    in.close();
  }
}
