/* 
 * 2.
 * O custo de um carro novo ao consumidor é a soma do custo de fábrica com a porcentagem
 * do distribuidor e dos impostos (aplicados ao custo de fábrica). Supondo que o percentual do
 * distribuidor seja de 28% e os impostos de 45%, escrever um algoritmo para ler o custo de
 * fábrica de um carro, calcular e escrever o custo final ao consumidor.
 */

import java.util.Scanner;

public class Carro {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    float custoFabrica, distribuidor, impostos, preco;
    final float percDistribuidor = 0.28f;
    final float percImpostos = 0.45f;

    System.out.println("Digite o valor do custo de fábrica:");
    System.out.print("R$ ");
    custoFabrica = in.nextFloat();

    distribuidor = custoFabrica * percDistribuidor;
    impostos = custoFabrica * percImpostos;

    preco = custoFabrica + distribuidor + impostos;

    System.out.printf("O custo ao consumidor será de R$ %.2f\n", preco);

    in.close();
  }
}