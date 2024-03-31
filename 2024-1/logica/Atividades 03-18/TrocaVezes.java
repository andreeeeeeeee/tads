/*
 * Leia dois valores A e B e os troque de lugar caso A > B
 */

import java.util.Scanner;

public class TrocaVezes {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int a, b, c = 0;

        System.out.println("Digite os valores de A e B:");
        a = in.nextInt();
        b = in.nextInt();

        if (a > b) {
            c = a;
            a = b;
            b = c;
        }

        System.out.printf("A: %d\nB: %d\n", a, b);

        in.close();
    }
}
