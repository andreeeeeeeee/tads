/*
 * 4.
 * Operações com Caracteres:
 * Solicite ao usuário para digitar um único caractere.
 * Imprima o código ASCII desse caractere e, em seguida, converta o
 * caractere para maiúsculo se for uma letra minúscula, ou para minúsculo
 * se for uma letra maiúscula. Imprima o resultado.
 */

import java.util.Scanner;

public class ASCII {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String maiMin;
        char texto, texto2;
        int ascii;

        System.out.println("Digite uma letra:");
        texto = in.next().charAt(0);

        
        ascii = (int) texto;

        System.out.println("O código ASCII de " + texto + " é " + ascii);

        if (ascii < 97) {
            ascii = ascii + 32;
            maiMin = " minúscula ";
        } else {
            ascii = ascii - 32;
            maiMin = " maiúscula ";
        }

        texto2 = (char) ascii;

        System.out.println("A letra " + texto + maiMin + "é " + texto2);

        in.close();
    }
}