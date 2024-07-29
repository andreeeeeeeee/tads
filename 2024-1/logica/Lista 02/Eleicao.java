/*
 * 8.
 * Escreva um algoritmo para ler o número total de eleitores de um município, o número de votos brancos, nulos e válidos.
 * Calcular e escrever o percentual que cada um representa em relação ao total de eleitores
 */

import java.util.Scanner;

public class Eleicao {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    System.out.println("Digite o número total de eleitores:");
    float eleitores = in.nextInt();
    System.out.println("Digite o número total de votos válidos:");
    float validos = in.nextInt();
    System.out.println("Digite o número total de votos brancos:");
    float brancos = in.nextInt();
    System.out.println("Digite o número total de votos nulos:");
    float nulos = in.nextInt();
    in.close();

    float percValidos = validos / eleitores * 100;
    float percBrancos = brancos / eleitores * 100;
    float percNulos = nulos / eleitores * 100;

    System.out.println("Dos " + (int) eleitores + " eleitores:");
    System.out.println(percValidos + "% (" + (int) validos + ") são válidos;");
    System.out.println(percBrancos + "% (" + (int) brancos + ") são brancos, e");
    System.out.println(percNulos + "% (" + (int) nulos + ") são nulos.");
  }
}