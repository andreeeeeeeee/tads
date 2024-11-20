import java.util.ArrayList;
import java.util.List;

class App {
    public static void main(String[] args) {

        List<String> disciplinas = List.of(
            "Programação Modular",
            "Design de Interfaces",
            "Engenharia de Software",
            "Projeto e Modelagem de Bancos de Dados",
            "Matemática Discreta",
            "Organização de Computadores"
        );

        List<Integer> cargasHorarias = List.of(
            66,
            66,
            66,
            33,
            66,
            33
        );

        List<String> professores = List.of(
            "Márcio Torres",
            "Tiago Telecken",
            "Cleber Fonseca",
            "Igor Pereira",
            "Javier Lopez",
            "Eduardo Brião"
        );

        List<String> vazia = new ArrayList<>();

        // Método => Contrato (API: Application Programming Interface)
        String p1 = Listas.primeiro(professores);
        System.out.println(p1); // Márcio Torres

        String p2 = Listas.segundo(professores);
        System.out.println(p2); // Tiago Telecken
        
        String p3 = Listas.ultimo(professores);
        System.out.println(p3); // Eduardo Brião

        String p4 = Listas.penultimo(professores);
        System.out.println(p4); // Javier Lopez

        String p5 = Listas.primeiro(vazia);
        System.out.println(p5); // null

        List<String> disc2 = Listas.sem(disciplinas, "Programação Modular");
        System.out.println(disc2); // [Design de Interfaces", Engenharia de Software, Projeto e Modelagem de Bancos de Dados, Matemática Discreta, Organização de Computadores]

        List<String> disc3 = Listas.mesclar(disciplinas, professores);
        System.out.println(disc3); // Programação Modular, Design de Interfaces, Engenharia de Software, Projeto e Modelagem de Bancos de Dados, Matemática Discreta, Organização de Computadores, Márcio Torres,, Tiago Telecken, Cleber Fonseca, Igor Pereira, Javier Lopez, Eduardo Brião
        
        // TODO: remover todas as ocorrências
        List<String> disc4 = Listas.remover(professores, List.of("Javier Lopez", "Márcio Torres"));
        System.out.println(disc4);

        List<String> disc5 = Listas.combinar(professores, disciplinas);
        System.out.println(disc5); // Márcio Torres, Programação Modular, Tiago Telecken, Design de Interfaces, Cleber Fonseca, Engenharia de Software, Igor Pereira, Projeto e Modelagem de Bancos de Dados, Javier Lopez, Matemática Discreta, Eduardo Brião, Organização de Computadores

        //List<String> outra = List.of("a", null, "b", null, "c");
        //System.out.println(Listas.compactar(outra)); // [a, b, c]

        boolean igual1 = Listas.igual(professores, disciplinas);
        System.out.println(igual1); // false

        boolean igual2 = Listas.igual(List.of("a", "b"), List.of("b", "a"));
        System.out.println(igual2); // false

        boolean igual3 = Listas.igual(List.of("c", "d"), List.of("c", "d"));
        System.out.println(igual3); // true

        boolean igual4 = Listas.igual(professores, professores);
        System.out.println(igual4); // true

        // // desafio:
        // List<List<String>> agrupado = Listas.agrupar(professores, 2);
        // System.out.println(agrupado); // [[Márcio Torres, Tiago Telecken], [Cleber Fonseca, Igor Pereira], [Javier Lopez, Eduardo Brião]]



    }
}
