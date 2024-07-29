/*
 * 9.
 * Escreva um programa que pergunte o raio de uma circunferência,
     * e em seguida mostre o diâmetro, comprimento e área da circunferência.
 * Considere PI = 3.141592
 */

import java.util.Scanner;

public class Circunferencia {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    final float PI = 3.141592f;

    System.out.println("Digite o raio da circunferência:");
    float r = in.nextFloat();

    float d = 2 * r;
    float C = 2 * PI * r;
    float A = PI * r * r;

    System.out.printf("Diâmetro: %.2f\nCircunferência: %.2f\nárea: %.2f\n", d, C, A);

    in.close();
  }
}
