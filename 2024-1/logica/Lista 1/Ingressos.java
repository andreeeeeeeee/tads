/*
 * 10.
 * Desenvolva um algoritmo que será utilizado para automatizar o cálculo do público e
 * da renda total de um evento esportivo. Este evento esportivo possui um valor fixo
 * cobrado por ingresso, no entanto, os sócios do clube em cujas dependências ocorre
 * o evento possuem um desconto de 30% no valor do ingresso e as crianças menoresde 10 anos não pagam ingresso.
 * Baseado nos dados acima apresentados o usuário deverá digitar 4 informações de entrada para o sistema, são elas:
 *  * Valor de cada ingresso
 *  * Número de pessoas (público do evento) que são sócias do clube
 *  * Número de pessoas (público do evento) não pagantes (menores de 10 anos)
 *  * Número de pessoas (público do evento) pagantes (sem desconto algum)
 * O algoritmo deverá calcular e exibir o público total do evento, a renda total do evento
 * e o valor que deixou de ser arrecadada devido aos descontos e isenções.
 */

import java.util.Scanner;

public class Ingressos {
    public static void main(String[] main) {
        // DECLARACAO
        Scanner in;

        // INICIALIZACAO
        in = new Scanner(System.in);    // TERMINAL / ENTRADA DO USUARIO
        
        // tipo / nomes
        float valorIngresso, valorDesconto, totalRenda, totalIngressos, totalDesconto;
        int pagantes, naoPagantes, socios, totalPublico;

        System.out.println("Valor de cada ingresso, em reais:");
        valorIngresso = in.nextFloat();
        valorDesconto = valorIngresso * 0.3f;
        System.out.println("Número de pessoas (público do evento) que são sócias do clube:");
        socios = in.nextInt();
        System.out.println("Número de pessoas (público do evento) não pagantes (menores de 10 anos):");
        naoPagantes = in.nextInt();
        System.out.println("Número de pessoas (público do evento) pagantes (sem desconto algum):");
        pagantes = in.nextInt();
        in.close();

        totalPublico = socios + naoPagantes + pagantes;
        totalIngressos = totalPublico * valorIngresso;
        totalRenda = pagantes * valorIngresso + socios * (valorIngresso - valorDesconto);
        totalDesconto = totalIngressos - totalRenda;

        System.out.print("O evento teve um público total de " + totalPublico + " pessoas, ");
        System.out.println("gerando uma renda total de R$ " + totalRenda + ".");
        System.out.println("O valor acumulado de isenções e descontos foi de R$ " + totalDesconto);
    }
}
