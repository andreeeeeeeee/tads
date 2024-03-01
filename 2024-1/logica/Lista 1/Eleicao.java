/*
 * 8.
 * Desenvolva o algoritmo para calcular o resultado de uma eleição de um municipal.
 * A entrada de dados será:
    ● Número total de eleitores;
    ● Número de votos válidos;
    ● Número de votos brancos;
    ● Número de votos nulos.
 * O algoritmo deve calcular e exibir para o usuário as seguintes informações (em
 * relação ao total de eleitores):
    ● Percentual de votos válidos;
    ● Percentual de votos brancos;
    ● Percentual de votos nulos;
 */

import java.util.Scanner;

 public class Eleicao {
    public static void main(String[] args) {
        // DECLARACAO
        Scanner in;

        // INICIALIZACAO
        in = new Scanner(System.in);    // TERMINAL / ENTRADA DO USUARIO
        
        // tipo / nomes
        float eleitores, validos, brancos, nulos, percValidos, percBrancos, percNulos;

        System.out.println("Digite o número total de eleitores:");
        eleitores = in.nextInt();
        System.out.println("Digite o número total de votos válidos:");
        validos = in.nextInt();
        System.out.println("Digite o número total de votos brancos:");
        brancos = in.nextInt();
        System.out.println("Digite o número total de votos nulos:");
        nulos = in.nextInt();
        in.close();

        percValidos = validos / eleitores * 100;
        percBrancos = brancos / eleitores * 100;
        percNulos = nulos / eleitores * 100;
        
        System.out.println("Dos " + (int)eleitores + " eleitores:");
        System.out.println(percValidos + "% (" + (int)validos + ") são válidos;");
        System.out.println(percBrancos + "% (" + (int)brancos + ") são brancos;");
        System.out.println(percNulos + "% (" + (int)nulos + ") são nulos;");
    }
 }