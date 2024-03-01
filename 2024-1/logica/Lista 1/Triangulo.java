/*
 * 4.
 * Desenvolva um algoritmo para calcular a área de um triângulo. Pensem nas
 * variáveis que serão necessárias. Ao final, o algoritmo deve informar a área total do
 * triângulo. 
 */

import java.util.Scanner;

public class Triangulo {
    public static void main(String[] args) {
        Scanner in;

        in = new Scanner(System.in);

        float base, altura, area;

        System.out.println("Digite o valor da base do triângulo:");
        base = in.nextFloat();
        System.out.println("Digite o valor da altura do triângulo:");
        altura = in.nextFloat();
        in.close();

        area = base * altura / 2;

        System.out.println("A área do triângulo é " + area);
    }  
}

