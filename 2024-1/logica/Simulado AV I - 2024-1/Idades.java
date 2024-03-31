/*
 * 8.
 * Escreva um programa que faça a leitura da idade de 2 homens e 2 mulheres (supondo que as
 * idades do mesmo sexo serão diferentes). O programa deve fazer o somatório da idade do
 * homem mais velho com a idade da mulher mais nova e o produto da idade do homem mais
 * novo pela idade da mulher mais velha. Ao final você deve imprimir os resultados, e cada uma
 * das idades digitadas: mulher mais nova, mulher mais velha, homem mais novo e homem mais velho.
 */

import java.util.Scanner;

public class Idades {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int h1, h2, m1, m2, velho, novo, velha, nova, soma, produto;

    System.out.println("Digite a idade dos dois homens e das duas mulheres, respectivamente:");
    h1 = in.nextInt();
    h2 = in.nextInt();
    m1 = in.nextInt();
    m2 = in.nextInt();

    velho = Math.max(h1, h2);
    novo = Math.min(h1, h2);
    velha = Math.max(m1, m2);
    nova = Math.min(m1, m2);

    soma = velho + nova;
    produto = novo * velha;

    System.out.printf("%d + %d = %d\n", velho, nova, soma);
    System.out.printf("%d * %d = %d\n", novo, velha, produto);

    in.close();
  }
}
