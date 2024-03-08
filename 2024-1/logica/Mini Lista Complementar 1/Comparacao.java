/*
 * 7.
 * Comparação de Números:
 * Peça ao usuário para inserir dois números.
 * Imprima se o primeiro número é maior, menor ou igual ao segundo.
 */

import java.util.Scanner;

public class Comparacao {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int v1, v2;

        System.out.println("Digite dois números:");
        v1 = in.nextInt();
        v2 = in.nextInt();

        if (v1 > v2) {
            System.out.println(v1 + " é maior que " + v2);
        } else if (v1 == v2) {
            System.out.println(v1 + " é igual a " + v2);
        } else {
            System.out.println(v1 + " é menor que " + v2);
        }

        in.close();
    }
}