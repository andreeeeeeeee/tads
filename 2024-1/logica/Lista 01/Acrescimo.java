/*
 * 2.
 * O algoritmo deve ter como entrada um número real e a saída deve ser o valor
 * atualizado com os 20%.
 */

import java.util.Scanner;

public class Acrescimo {

  public static void main(String[] args) {

    // DECLARACAO
    Scanner in;

    // INICIALIZACAO
    in = new Scanner(System.in); // TERMINAL / ENTRADA DO USUARIO

    // tipo / nome
    int numero;
    float acrescimo, total;

    System.out.println("Digite um número inteiro:");
    numero = in.nextInt(); // a variavel numero recebe o inteiro que for digitado
    in.close();

    acrescimo = numero * 0.2f;
    total = numero + acrescimo;

    System.out.println(numero + " mais 20% (" + acrescimo + ") é " + total);
  }
}