/*
 * 4.
 * Escreva um programa que faça a leitura de dois valores inteiros e descubra qual deles é o maior,
     * imprimindo na resposta o nome da variável e o seu valor.
 */

import java.util.Scanner;

public class Maior {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    System.out.println("Digite o valor de a:");
    int a = in.nextInt();
    System.out.println("Digite o valor de b:");
    int b = in.nextInt();

    if (a > b)
      System.out.printf("a = %d\n", a);
    else if (a < b)
      System.out.printf("b = %d\n", b);
    else
      System.out.printf("a e b = %d\n", a);

    in.close();
  }
}