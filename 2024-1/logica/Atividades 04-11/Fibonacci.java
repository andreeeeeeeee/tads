import java.util.Scanner;

public class Fibonacci {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int i =0, soma=1, ultimo = 0;

        System.out.println("Digite a quantidade de números da sequência:");
        int quantidade = in.nextInt();

        while (i < quantidade) {
            System.out.println(soma);
            if (i>0){
                soma += ultimo;
            }
            
            ultimo = soma - ultimo;
            i++;
        }

        in.close();
    }
}
