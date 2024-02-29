/*
 * 1.
 * Desenvolva o algoritmo de um programa onde o usuário irá informar um número
 * inteiro e o programa deve calcular e exibir o número imediatamente antecessor ao
 * número digitado pelo usuário.
 */

import java.util.Scanner;

public class Antecessor {

    public static void main(String[] args) {

        // DECLARACAO
        Scanner in;

        // INICIALIZACAO
        in = new Scanner(System.in);    // TERMINAL / ENTRADA DO USUARIO
        
        // tipo / nome
        int numero;

        System.out.println("Digite um número inteiro:");
        numero = in.nextInt();       // a variavel numero recebe o inteiro que for digitado


        System.out.println("O antecessor de " + numero + " é " + (numero-1));

    }
}