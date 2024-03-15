/*
 * Leio os valores que leiam os preços do álcool e da gasolina e, sabendo que o valor
 * do álcool deve ser mais de 30% mais barato que o da gasolina, informe ao usuário
 * qual combustível deve ser escolhido, dando prioridade à gasolina em caso de empate.
 */

import java.util.Scanner;

public class AlcoolGasol {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        float valorAlcool, valorGasol;
        final float multiplicador = 0.3f;

        System.out.println("Digite o valor do álcool:");
        valorAlcool = in.nextFloat();
        System.out.println("Digite o valor da gasolina:");
        valorGasol = in.nextFloat();

        if (valorAlcool + valorGasol * multiplicador < valorGasol)
            System.out.println("Àlcool");
        else
            System.out.println("Gasolina");

        in.close();
    }
}
