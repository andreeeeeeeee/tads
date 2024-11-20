package list;

import java.util.List;

public class App {
    public static void main(String[] args) {
        String teste = "nome;end;telefone";
        char separador = ';';

        List<String> partes = Texto.split(teste, separador);

       System.out.println(partes);
    }
}