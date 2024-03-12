/*
 * Leia a quantidade de um item e o seu valor, e aplique um desconto no valor final de
 *  *   5% a cada 3 itens
 */

import java.util.Scanner;

public class Desconto {
    public static void main(String[] args) {
        
        Scanner in = new Scanner(System.in);

        int quantidade;
        float valorUni, valorDes, valorTot, valorFin, percDes;

        System.out.println("Digite a quantidade de items comprados:");
        quantidade = in.nextInt();
        System.out.println("Digite o valor de cada item:");
        valorUni = in.nextFloat();

        valorTot = quantidade * valorUni;
        percDes = ((int) quantidade / 3) * 0.05f;
        valorDes = valorTot * percDes;
        valorFin = valorTot - valorDes;

        System.out.printf("%02.2f", valorFin);

        in.close();
    }
}
