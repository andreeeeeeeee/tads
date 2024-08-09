import java.util.Scanner;

public class Pascal {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Digite o número de linhas: ");
        int n = in.nextInt();
        in.close();

        if (n >= 0) System.out.println("[ 1 ]");
        if (n >= 1) System.out.println("[ 1 1 ]");
        int [] vetor = {1,1};

        for (int i = 1; i <= n-2; i++) {
            int [] outroVetor = new int[vetor.length+1];

            outroVetor[0] = 1;
            outroVetor[outroVetor.length-1] = 1;

            for (int j = 1; j < outroVetor.length-1; j++) {
                outroVetor[j] = vetor[j-1] + vetor[j];
            }

            vetor = outroVetor;

            System.out.print("[ ");
            for (int j = 0; j < outroVetor.length; j++) {
                System.out.print(outroVetor[j] + " ");
            }
            System.out.print("]\n");
        }
    }
}
