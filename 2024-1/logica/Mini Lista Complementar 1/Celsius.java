/*
 * 8.
 * Conversão de Temperatura:
 * Solicite ao usuário para inserir uma temperatura em Celsius.
 * Converta essa temperatura para Fahrenheit e imprima o resultado.
 * A fórmula é (Celsius * 9/5) + 32.
 */


import java.util.Scanner;

public class Celsius {
    public static void main(String[] args) {
        Scanner in;

        in = new Scanner(System.in);

        float fahrenheit, celsius;

        System.out.println("Digite uma temperatura em Celsius:");
        celsius = in.nextFloat();
        
        fahrenheit = (celsius * 9 / 5) + 32;

        System.out.println(celsius + "°C = " + fahrenheit + "°F");
        
        in.close();
    }  
}