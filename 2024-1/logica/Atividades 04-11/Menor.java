/*
 * 1.
 * DADO 10 VALORES DIGITADOS PELO USUARIO, INDIQUE QUAL O MENOR VALOR.
 */

import java.util.Scanner;

public class Menor {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int i = 0, numero = 0, menor = 0;

    while (i < 10) {
      i++;
      System.out.printf("Digite o %d° valor: ", i);
      numero = in.nextInt();
      if (i == 1) {
        menor = numero;
      }
      if (menor > numero) {
        menor = numero;
      }
    }

    in.close();

    System.out.printf("Menor: %d\n", menor);
  }
}
