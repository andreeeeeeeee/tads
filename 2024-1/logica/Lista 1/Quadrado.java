/*
 * 3.
 * Desenvolva o algoritmo de um programa onde o usuário irá informar um número
 * inteiro e o programa deve calcular e exibir quadrado do número informado pelo
 * usuário.
 */

import java.util.Scanner;

public class Quadrado {
  public static void main(String[] args) {
    Scanner in;

    in = new Scanner(System.in);

    int numero, quadrado;

    System.out.println("Digite um número inteiro:");
    numero = in.nextInt();
    in.close();

    quadrado = numero * numero;

    System.out.println(numero + "² = " + quadrado);
  }
}
