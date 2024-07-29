/*
 * 3.
 * Ler um valor e escrever se é positivo ou negativo (considere o valor zero como positivo).
 */

import java.util.Scanner;

public class PositivoNegativo {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    System.out.println("Digite um valor:");
    int n = in.nextInt();
    String resultado = "";

    if (n >= 0)
      resultado = "positivo";
    else
      resultado = "negativo";

    System.out.printf("%d é %s\n", n, resultado);
    in.close();
  }
}