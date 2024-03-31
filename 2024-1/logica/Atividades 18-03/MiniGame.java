/*
 * Leia a pontuação de duas cartas, ataque e defesa, respectivamente.
 * O programa deve sortear um valor (0 para ataque e 1 para defesa)
 * e dizer qual a carta ganhadora.
 * 
 * Para sortear um valor você irá usar a classe random
 */

import java.util.Scanner;
import java.util.Random;

public class MiniGame {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Random rand = new Random();

        int ataque, defesa, vencedor;

        System.out.println("Digite a pontuação da carta de ataque:");
        ataque = in.nextInt();
        System.out.println("Digite a pontuação da carta de defesa:");
        defesa = in.nextInt();

        vencedor = rand.nextInt(2);

        if (vencedor == 0) {
          System.out.println("A carta de ataque venceu.");
        } else {
          System.out.println("A carta de defesa venceu.");
        }

        in.close();
    }
}
