/*
 * 6.
 * Desenvolva o algoritmo de um programa para calcular a média de duas notas das
 * avaliações de um aluno.
 */

import java.util.Scanner;

public class Media {
    public static void main(String[] args) {
        Scanner in;

        in = new Scanner(System.in);

        double nota1, nota2;

        System.out.println("Digite o valor da primeira nota:");
        nota1 = in.nextDouble();
        System.out.println("Digite o valor da segunda nota:");
        nota2 = in.nextDouble();

        System.out.println("A média entre as duas notas é " + ((nota1 + nota2) / 2));
    }  
}

