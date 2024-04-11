// mostrar os numeros multiplos de 3 em um intervalo de [a,b] escolhido pelo usuario

import java.util.Scanner;

public class Multiplos3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int a = in.nextInt(), b = in.nextInt();

        while (a <= b) {
            if (a%3 == 0) {
                System.out.println(a);
            }
            
            a++;
        }
        in.close();
    }
}