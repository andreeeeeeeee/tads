/*
 * 2.
 * Leia 10 valores quantos pares e quantos sao impares
 */

import java.util.Scanner;

public class ParesImpares {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int i = 0,pares = 0, impares = 0;

        while (i < 10) {
            i++;
            System.out.printf("Digite o %d° valor: ", i);
            int numero = in.nextInt();
            if (numero%2 == 0)
                pares++;
            else
                impares++;
        }

        in.close();

        System.out.printf("Pares: %d\nÍmpares: %d\n", pares, impares);
    }
}
