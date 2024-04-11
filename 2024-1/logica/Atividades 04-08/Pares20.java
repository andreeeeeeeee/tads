// mostrar os numeros pares de 0 a 20

public class Pares20 {
    public static void main(String[] args) {
        int i = 0;

        while (i <= 20) {
            if (i % 2 == 0) {
                System.out.println(i);
            }
            i++;
        }
    }
}