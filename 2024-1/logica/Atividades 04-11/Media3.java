/*
 * 3.
 * Calcule a média simples entre a quantidade de valores digitada pelo usuário.
 * A menor nota será desconsiderada
 */

import java.util.Scanner;

public class Media3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int i = 0, quantidade;
        float soma = 0, numero = 0, menor = 0;

        System.out.println("Com quantos valores você deseja calcular a média?");
        quantidade = in.nextInt();

        while (i < quantidade) {
            i++;
            System.out.printf("Digite o %d° valor: ", i);
            numero = in.nextFloat();
            soma += numero;
            if (i==1) {
                menor = numero;
            } else if (numero < menor) {
                menor = numero;
            }
        }
        in.close();

        quantidade--;

        float media = (soma - menor) / quantidade;

        System.out.printf("A média entre os %d maiores valores é de %.2f\n", quantidade, media);
    }
}