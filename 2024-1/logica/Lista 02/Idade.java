/*
 * 5.
 * Faça um algoritmo que leia a idade de uma pessoa expressa em anos, meses e dias e escreva a idade dessa pessoa expressa apenas em dias.
 * Considerar ano com 365 dias e mês com 30 dias. Calcular quantos dias a pessoa já viveu até hoje.
 *    a. Desafio e pesquisa: utilizando a classe Calendar ou Date do Java, peça que o usuário informe sua data de nascimento
 *    e o sistema irá calcular quantos anos, meses e dias a pessoa viveu.
 */

import java.util.Scanner;

public class Idade {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    System.out.println("Digite sua idade em anos, meses e dias:");
    int anos = in.nextInt();
    int meses = in.nextInt();
    int dias = in.nextInt();

    int diasTotais = anos * 365 + meses * 30 + dias;

    System.out.printf("Você já viveu %d dias\n", diasTotais);

    in.close();
  }
}