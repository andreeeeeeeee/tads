/*
 * 5.
 * Desenvolva o algoritmo para converter uma temperatura em graus Fahrenheit para
 * graus Celsius.
 * A fórmula para conversão é a seguinte:
 *  *   C/5 = (F-32)/9
 * Onde:
 *  *   C = temperatura em graus Celsius;
 *  *   F = temperatura em graus Fahrenheit.
 */

import java.util.Scanner;

public class Fahrenheit {
    public static void main(String[] args) {
        Scanner in;

        in = new Scanner(System.in);

        double fahrenheit;

        System.out.println("Digite uma temperatura em Fahreheint:");
        fahrenheit = in.nextDouble();

        System.out.println(fahrenheit + "°F = " + ((fahrenheit - 32) / 9 * 5) + "°C");
    }  
}

