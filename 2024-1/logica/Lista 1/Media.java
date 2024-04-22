/*
 * 6.
 * Desenvolva o algoritmo de um programa para calcular a média de duas notas das
 * avaliações de um aluno.
 */

import java.util.Scanner;

public class Media {
  public static void main(String[] args) {
    Scanner in;

    in = new Scanner(System.in);

    float nota1, nota2, media;

    System.out.println("Digite o valor da primeira nota:");
    nota1 = in.nextFloat();
    System.out.println("Digite o valor da segunda nota:");
    nota2 = in.nextFloat();
    in.close();

    media = (nota1 + nota2) / 2;

    System.out.println("A média entre as duas notas é " + media);
  }
}
