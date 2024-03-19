/*
 * Leia a pontuação de duas cartas, ataque e defesa, respectivamente.
 * O programa deve sortear um valor (0 para ataque e 1 para defesa)
 * e dizer qual a carta ganhadora.
 * 
 * Para sortear um valor você irá usar a classe random
 */

import java.util.Scanner;

public class MiniGame {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int ataque, defesa, vencedor;

        // System.out.println("Digite a pontuação da carta de ataque:");
        // ataque = in.nextInt();
        // System.out.println("Digite a pontuação da carta de defesa:");
        // defesa = in.nextInt();

        vencedor = (int) Math.random()*10;

        System.out.printf("%d\n", vencedor);

        in.close();
    }
}
