import java.util.Scanner;

public class TrocaPosicao {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int [] v = {21,45,231,45,21,87,65,7,4,5,45,2,84,21,85,3,84,6,645,8};

        while (true) {
            System.out.print("[ ");
            for (int i = 0; i < v.length; i++) {
                System.out.print(v[i] + " ");
            }
            System.out.print("]\n");

            System.out.print("Digite a primeira posição: ");
            int posicao1 = in.nextInt();
            System.out.print("Digite a segunda posição: ");
            int posicao2 = in.nextInt();

            System.out.println();

            if (posicao1 >=0 && posicao1 <v.length && posicao2 >=0 && posicao2 <v.length) {
                int valor = v[posicao2];
                v[posicao2] = v[posicao1];

                v[posicao1] = valor;
            } else break;
        }

        in.close();
    }
}
