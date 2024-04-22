/*
 * 1.
 * Conversão de Tipos:
 * Peça ao usuário para inserir dois números: um inteiro e um decimal (float).
 * Em seguida, converta o número inteiro em um tipo float e o número decimal
 * em um tipo inteiro, e imprima ambos.
 */

import java.util.Scanner;

public class Conversao {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int inteiro, decInteiro;
    float decimal, intDecimal;

    System.out.println("Digite um número inteiro:");
    inteiro = in.nextInt();
    System.out.println("Digite um número decimal:");
    decimal = in.nextFloat();

    intDecimal = (float) inteiro;
    decInteiro = (int) decimal;

    System.out.println("Número inteiro convertido em decimal: " + intDecimal);
    System.out.println("Número decimal convertido em inteiro: " + decInteiro);

    in.close();
  }
}
