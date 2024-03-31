/* RECEBA UM TEMPO EM SEGUNDOS E CONVERTA PARA O FORMATO HH:mm:ss */

import java.util.Scanner;

public class ConversaoTempo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int segundosTotais, segundos, minutos, horas;

        System.out.println("Digite o total de tempo em segundos:");
        segundosTotais = in.nextInt();

        segundos = segundosTotais % 60;
        minutos = segundosTotais % 3600 / 60;
        horas = segundosTotais / 3600;
        
        /*
         * %d => int
         * %f => float / double
         * %s => string
         */

        System.out.printf("%02d:%02d:%02d\n", horas, minutos, segundos);
        in.close();
    }
}
