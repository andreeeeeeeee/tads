/*
 * Leia a idade do usuário e retorne se ele é maior ou menor de idade, e caso seja maior,
 * retornar se é idoso (65+ anos)
 */

import java.util.Scanner;

public class Idade {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int idade;

    System.out.println("Digite sua idade:");
    idade = in.nextInt();

    if (idade < 18)
      System.out.println("Você é menor de idade");
    else if (idade < 65)
      System.out.println("Você é maior de idade");
    else
      System.out.println("Você é maior de idade e idoso");

    in.close();
  }
}
