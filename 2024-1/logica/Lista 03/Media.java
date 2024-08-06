/*
 * 5.
 * Faça um programa que receba a 5 notas de um aluno, através do comando while,
 * e que apresente ao final a média dessas 5 notas.
 * Você deverá ter apenas uma variável nota, e uma variável média.
 */

import java.util.Scanner;

public class Media {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    float nota = 0;
    int i = 0;
    while (i < 5) {
      float n = in.nextFloat();

      nota += n;
      i++;
    }

    float media = nota / 5;

    System.out.println(media);
    in.close();
  }
}
