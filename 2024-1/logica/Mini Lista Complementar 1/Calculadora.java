/*
 * 2.
 * Cálculo Simples:
 * Solicite ao usuário para inserir dois números.
 * Realize a soma, subtração, multiplicação e divisão desses números. 
 * Imprima o resultado de cada uma dessas operações.
 */

import java.util.Scanner;

public class Calculadora {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int v1, v2, soma, subtracao, multiplicacao;
    float divisao;

    System.out.println("Digite dois valores:");
    v1 = in.nextInt();
    v2 = in.nextInt();

    soma = v1 + v2;
    subtracao = v1 - v2;
    multiplicacao = v1 * v2;
    divisao = (float) v1 / v2;

    System.out.println(v1 + " + " + v2 + " = " + soma);
    System.out.println(v1 + " - " + v2 + " = " + subtracao);
    System.out.println(v1 + " * " + v2 + " = " + multiplicacao);
    System.out.println(v1 + " / " + v2 + " = " + divisao);

    in.close();
  }
}