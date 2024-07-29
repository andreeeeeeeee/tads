/*
 * 2.
 * Escreva um algoritmo para ler as dimensões de um retângulo (base e altura),
     * calcular e escrever a área e o perímetro do retângulo.
 */

import java.util.Scanner;

public class Retangulo {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    System.out.println("Digite a base do retângulo:");
    float base = in.nextFloat();
    System.out.println("Digite a altura do retângulo:");
    float altura = in.nextFloat();

    float area = base * altura;
    float perimetro = base * 2 + altura * 2;

    System.out.printf("O retângulo possui uma área de %.2f m² e um perímetro de %.2f m\n", area, perimetro);

    in.close();
  }
}