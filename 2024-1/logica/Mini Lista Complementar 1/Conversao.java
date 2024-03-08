/*
 * 1.
 * Conversão de Tipos:
 * Peça ao usuário para inserir dois números: um inteiro e um decimal (float).
 * Em seguida, converta o número inteiro em um tipo float e o número decimal
 * em um tipo inteiro, e imprima ambos.
 */

import java.util.Scanner;

public class Conversao {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int inteiro;
        float decimal;

        System.out.println("Digite um número inteiro:");
        inteiro = in.nextInt();
        System.out.println("Digite um número decimal:");
        decimal = in.nextFloat();

        decimal = (float) inteiro;
        inteiro = (int) decimal;

        System.out.println("Número inteiro convertido em decimal: " + decimal);
        System.out.println("Número decimal convertido em inteiro: " + inteiro);

        in.close();
    }
}