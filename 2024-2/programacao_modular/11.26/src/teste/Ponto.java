package teste;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class Ponto {
    static List<String> ler(String data) {
        List<String> res = new ArrayList<>();
        Path arquivo = Path.of(data + ".yml");

        try {
            List<String> linhas = Files.readAllLines(arquivo);
            String entrada = linhas.get(0);
            String saida = linhas.get(1);

            res.add(entrada.substring(entrada.indexOf(':')+2));
            res.add(saida.substring(saida.indexOf(':')+2));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return res;
    }
}
