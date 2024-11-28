package teste;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class App {
    public static void main(String[] args) {
        List<String> array = Ponto.ler("2024-11-26");

        for (String string : array) {
            System.out.println(string);
        }

        Map<String, String> pessoa = new HashMap<>();
        pessoa.put("nome", "André");
        pessoa.put("sobrenome", "Bastilhos");
        System.out.println(pessoa);
    }
}