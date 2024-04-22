/*
 * 1.
 * Calcule a média simples entre 5 valores digitados pelo usuário
 *  -   Não devem ser declaradas 5 variáveis de valor
 */

import java.util.Scanner;

public class Media {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int soma = 0, i = 0;

    while (i < 5) {
      i++;
      System.out.printf("Digite o %d° valor: ", i);
      int numero = in.nextInt();
      soma += numero;
    }
    in.close();

    float media = soma / 5f;

    System.out.printf("A média entre os 5 valores é de %.2f\n", media);
  }
}