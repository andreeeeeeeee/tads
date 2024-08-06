/*
 * 1.
 * Ler um número inteiro n. Escrever a soma de todos os números de 1 até n
 */

import java.util.Scanner;

public class Soma1n {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int n = in.nextInt();
    in.close();
    
    int soma = 0;

    for (int i = 1; i <= n; i++) {
      soma += i;
    }

    System.out.println(soma);
  }
}