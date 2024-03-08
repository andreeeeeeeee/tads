/*
 * 6.
 * Perímetro de um Retângulo:
 * Solicite ao usuário para inserir o comprimento e a largura de um retângulo.
 * Calcule e imprima o perímetro do retângulo.
 */

import java.util.Scanner;

public class Retangulo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        float larg, comp, perimetro;

        System.out.println("Digite, em metros, a largura e o comprimento de um retângulo, respectivamente:");
        larg = in.nextFloat();
        comp = in.nextFloat();

        perimetro = larg * 2 + comp * 2;

        System.out.println("O perímetro é de " + perimetro);

        in.close();
    }
}