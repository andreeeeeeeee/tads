/*
 * 1.
 * Ler um número inteiro n. Escrever a soma de todos os números de 1 até n
 */

import java.util.Scanner;

public class SomaPares {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int n = in.nextInt();
    in.close();
    
    int soma = 0;

    for (int i = 2; i <= n; i+=2) {
      soma += i;
    }

    System.out.println(soma);
  }
}