package array;

public class App {
    public static void main(String[] args) {
        String teste = "nome;end;telefone";
        char separador = ';';

        String[] partes = Texto.split(teste, separador);

        for (String parte : partes) {
            System.out.println(parte);
        }
    }
}