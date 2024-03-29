import java.util.Scanner;

public class Ingressos {
    public static void main(String[] main) {
        Scanner in;

        in = new Scanner(System.in);
        
        float valorIngresso, valorDesconto, totalRenda, totalIngressos, totalDesconto;
        int pagantes, naoPagantes, socios, totalPublico;

        System.out.println("Valor de cada ingresso, em reais:");
        valorIngresso = in.nextFloat();
        valorDesconto = valorIngresso * 0.3f;
        System.out.println("Número de pessoas (público do evento) que são sócias do clube:");
        socios = in.nextInt();
        System.out.println("Número de pessoas (público do evento) não pagantes (menores de 10 anos):");
        naoPagantes = in.nextInt();
        System.out.println("Número de pessoas (público do evento) pagantes (sem desconto algum):");
        pagantes = in.nextInt();
        in.close();

        totalPublico = socios + naoPagantes + pagantes;
        totalIngressos = totalPublico * valorIngresso;
        totalRenda = pagantes * valorIngresso + socios * (valorIngresso - valorDesconto);
        totalDesconto = totalIngressos - totalRenda;

        System.out.print("O evento teve um público total de " + totalPublico + " pessoas, ");
        System.out.println("gerando uma renda total de R$ " + totalRenda + ".");
        System.out.println("O valor acumulado de isenções e descontos foi de R$ " + totalDesconto);
    }
}