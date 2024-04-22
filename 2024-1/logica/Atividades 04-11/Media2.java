/*
 * 2.
 * Calcule a média simples entre a quantidade de valores digitada pelo usuário
 */

import java.util.Scanner;

public class Media2 {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int soma = 0, i = 0, quantidade;

    System.out.println("Com quantos valores você deseja calcular a média?");
    quantidade = in.nextInt();

    while (i < quantidade) {
      i++;
      System.out.printf("Digite o %d° valor: ", i);
      int numero = in.nextInt();
      soma += numero;
    }
    in.close();

    float media = (float) soma / (float) quantidade;

    System.out.printf("A média entre os %d valores é de %.2f\n", quantidade, media);
  }
}