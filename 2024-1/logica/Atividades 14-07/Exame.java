/*
 * CALCULAR A MEDIA DE 3 NOTAS DE UM ALUNO E RETORNAR A NOTA NECESSÁRIA NO EXAME
 * EX = (5 - MS * 0.6) / 0.4
 */

import java.util.Scanner;

public class Exame {
    public static void main(String[] args) {
        Scanner in;
 
        in = new Scanner(System.in);
 
        float v1, v2, v3, media, exame;
        final int APROVACAO = 7;
 
        System.out.println("Digite as três notas:");
        v1 = in.nextFloat();
        v2 = in.nextFloat();
        v3 = in.nextFloat();
        in.close();
 
        media = (v1 + v2 + v3) / 3;
 
        System.out.printf("A média entre as três notas é %.2f\n", media);

        exame = (5 - media * 0.6f) / 0.4f;
 
        if (media >= APROVACAO)
            System.out.println("Aprovado");
        else {
            System.out.println("Reprovado");
            System.out.printf("Você precisa de %.2f para ser aprovado no exame\n", exame);
        }
     }  
 }