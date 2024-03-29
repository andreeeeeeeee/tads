/*
 * 7.
 * Inversor de dígitos.
 * Faça um programa que dado um número de 4 dígitos, apenas utilizando
 * operações matemáticas, o programa devolva o número com os dígitos invertidos.
 * Por exemplo, para o número 1234, o resultado é 4321.
 * Considere valores entre 1000 e 9999.
 */

import java.util.Scanner;

public class Inversor {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        int num, numInv = 0, numOrig, milhar, centena, dezena, unidade;

        System.out.println("Digite um número:");
        numOrig = in.nextInt();

        num = numOrig;

        milhar = num / 1000;
        numInv += milhar;
        num -= milhar * 1000;

        centena = num / 100;
        numInv += centena * 10;
        num -= centena * 100;

        dezena = num / 10;
        numInv += dezena * 100;
        num -= dezena * 10;

        unidade = num;
        numInv += unidade * 1000;
        num -= unidade;

        System.out.printf("%d invertido: %d\n", numOrig, numInv);

        in.close();
    }
}
