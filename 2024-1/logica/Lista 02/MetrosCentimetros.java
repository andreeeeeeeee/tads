/*
 * 1.
 * Faça um algoritmo que converta metros para centímetros. Lembrando que 1m = 100cm
 */

import java.util.Scanner;

public class MetrosCentimetros {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    System.out.println("Digite a medida em metros:");
    float metros = in.nextFloat();

    float centimetros = metros * 100;

    System.out.printf("%f m = %.2f cm\n", metros, centimetros);

    in.close();
  }
}