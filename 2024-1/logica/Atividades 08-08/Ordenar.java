import java.util.Random;

public class Ordenar {
    public static void main(String[] args) {
        Random r = new Random();

        int tamanho = r.nextInt(11);

        int [] v = new int[tamanho];

        for (int i = 0; i < v.length; i++) {
            v[i] = r.nextInt(100);
        }

        while (true) {
            for (int i = 0; i < v.length-1; i++) {
                if (v[i] > v[i+1]) {
                    int valor = v[i];
                    v[i] = v[i+1];

                    v[i+1] = valor;
                }

                System.out.print("[ ");
                for (int j = 0; j < v.length; j++) {
                    System.out.print(v[j] + " ");
                }
                System.out.print("]\n");
            }
            int aux = 0;

            for (int i = 0; i < v.length-1; i++)
                if (v[i] <= v[i+1]) aux++;

            if (aux == v.length-1) break;
        }
    }
}
