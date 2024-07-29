/*
 * 7.
 * Faça um algoritmo que pergunte quanto a pessoa ganha por hora (salário por hora) e o número de horas trabalhadas no mês.
 * Calcule e mostre o total do seu salário no referido mês e qual será seu salário atual.
 * Calcule também o salário líquido (desconto de impostos) considerando 15% de impostos e mostre esses valores.
 *    a. Pergunte ao usuário qual a % de imposto que é descontada do salário.
 */

import java.util.Scanner;

public class Salario {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    System.out.println("Digite seu salário por hora:");
    float salarioPorHora = in.nextFloat();
    System.out.println("Digite horas trabalhadas no mês:");
    int horasTrab = in.nextInt();

    float salarioTotal = salarioPorHora * horasTrab;

    System.out.printf("Seu salário bruto: R$ %.2f\n", salarioTotal);

    System.out.println("Digite o percentual de desconto de impostos do salário:");
    float imposto = in.nextFloat();

    float salarioLiquido = salarioTotal * imposto / 100;

    System.out.printf("Seu salário líquido: R$ %.2f\n", salarioLiquido);

    in.close();
  }
}
