/*
 * 9.
 * Cálculo da Média:
 * Peça ao usuário para inserir três números.
 * Calcule e imprima a média desses números.
 */

import java.util.Scanner;

public class Media {
    public static void main(String[] args) {
        Scanner in;

        in = new Scanner(System.in);

        int v1, v2, v3, media;

        System.out.println("Digite três números:");
        v1 = in.nextInt();
        v2 = in.nextInt();
        v3 = in.nextInt();
        in.close();

        media = (v1 + v2 + v3) / 3;

        System.out.println("A média entre os três números é " + media);
    }  
}