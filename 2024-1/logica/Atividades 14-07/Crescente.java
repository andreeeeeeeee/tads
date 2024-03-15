/*
 * Leia três números e apresente-os em ordem crescente
 */
import java.util.Scanner;

public class Crescente {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n1, n2, n3;
        int maior = 0;
        int menor = 0;
        int meio = 0;
        System.out.println("Digite três números:");
        n1 = in.nextInt();
        n2 = in.nextInt();
        n3 = in.nextInt();

        if (n1 >= n2 && n1 >= n3) {
            maior = n1;
            if (n2>=n3) {
                meio = n2;
                menor = n3;
            } else {
                meio = n3;
                menor = n2;
            }
        } else if (n2 >= n1 && n2 >= n3) {
            maior = n2;
            if (n1>=n3) {
                meio = n1;
                menor = n3;
            } else {
                meio = n3;
                menor = n1;
            }
        } else if (n3 >= n1 && n3 >= n2) {
            maior = n3;
            if (n1>=n2) {
                meio = n1;
                menor = n2;
            } else {
                meio = n2;
                menor = n1;
            }
        }

        System.out.printf("%d, %d, %d\n", menor, meio, maior);

        in.close();
    }
} 
