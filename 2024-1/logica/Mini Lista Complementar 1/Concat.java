/*
 * 3.
 * Concatenação de Strings:
 * Peça ao usuário para digitar dois nomes.
 * Concatene (junte) esses nomes com um espaço
 * entre eles e imprima o resultado.
 */

import java.util.Scanner;

public class Concat {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String nome1, nome2, nome;

        System.out.println("Digite dois nomes:");
        nome1 = in.next();
        nome2 = in.next();

        nome = nome1 + " " + nome2;

        System.out.println(nome);

        in.close();
    }
}