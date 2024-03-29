/*
 * 4.
 * Escreva um programa que leia as medidas dos lados de um triângulo e escreva se ele
 * é Equilátero, Isósceles ou Escaleno. Sendo que:
 *  − Triângulo Equilátero: possui os 3 lados iguais.
 *  − Triângulo Isósceles: possui 2 lados iguais.
 *  − Triângulo Escaleno: possui 3 lados diferentes */

import java.util.Scanner;

public class Triangulo {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        float l1, l2, l3;

        System.out.println("Digite os 3 lados de um triângulo");
        l1 = in.nextFloat();
        l2 = in.nextFloat();
        l3 = in.nextFloat();

        if (l1 == l2 && l2 == l3) {
            System.out.println("Equilátero");
        } else if (l1 != l2 && l1 != l3 && l2 != l3){
            System.out.println("Escaleno");
        } else {
            System.out.println("Isósceles");
        }

        in.close();
    }
}
