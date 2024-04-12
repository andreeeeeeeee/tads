/*
 * 4.
 * Leia 10 valores e indique o maior e o menor valor
 */

import java.util.Scanner;

public class MaiorMenor {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int i = 0, numero = 0, menor = 0, maior = 0;

        while (i < 10) {
            i++;
            System.out.printf("Digite o %d° valor: ", i);
            numero = in.nextInt();
            if (i==1) {
                menor = numero;
                maior = numero;
            }
            if (menor > numero) {
                menor = numero;
            }
            if (maior < numero) {
                maior = numero;
            }
        }

        in.close();

        System.out.printf("Maior: %d\nMenor: %d\n", maior, menor);
    }
}
