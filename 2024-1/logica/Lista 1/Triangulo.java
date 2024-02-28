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

        double base, altura;

        System.out.println("Digite o valor da base do triângulo:");
        base = in.nextDouble();
        System.out.println("Digite o valor da altura do triângulo:");
        altura = in.nextDouble();

        System.out.println("A área do triângulo é " + (base * altura / 2));
    }  
}

