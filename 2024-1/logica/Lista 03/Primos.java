
/*
 * 3. 
 * Ler um número inteiro n. Decidir se n é um número primo e apresente o resultado
 */

import java.util.Scanner;

public class Primos {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int n = in.nextInt();
    in.close();
    int divisores = 0;

    for (int i = 1; i <= n; i++) {
      if (n % i == 0) {
        divisores++;
      }
      if (divisores > 2) {
        System.out.println(n + " não é primo.");
        break;
      }
    }

    if (divisores == 2) {
      System.out.println(n + " é primo.");
    }

  }
}
