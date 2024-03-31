/*
 * CALCULAR A MEDIA DE 3 NOTAS DE UM ALUNO
 */

import java.util.Scanner;

public class Media {
    public static void main(String[] args) {
        Scanner in;

        in = new Scanner(System.in);

        float v1, v2, v3, media;

        System.out.println("Digite as três notas:");
        v1 = in.nextFloat();
        v2 = in.nextFloat();
        v3 = in.nextFloat();
        in.close();

        media = (v1 + v2 + v3) / 3;

        System.out.printf("A média entre as três notas é %.2f\n", media);

        if (media >= 7)
            System.out.println("Aprovado");
        else
            System.out.println("Reprovado");
    }  
}