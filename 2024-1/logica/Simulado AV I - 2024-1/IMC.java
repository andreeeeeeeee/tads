/*
 * 9.
 * O IMC é um dos índices mais usados para classificar uma pessoa de acordo com o grau de
 * excesso de peso. O Índice de Massa Corporal é calculado dividindo o peso em quilos pela
 * altura ao quadrado.
 *  *   Valores menores que 18 kg/m² – Consideradas pessoas de baixo peso.
 *  *   Valores entre 18-24 kg/m² para mulheres / 18-25 kg/m² para homens – consideradas IMC de pessoas normais.
 *  *   Entre 24-30 para mulheres e 25-30 kg/m² para homens – consideradas pessoas com sobrepeso.
 *  *   Valores entre 30-35 kg/m² – pessoas com obesidade leve.
 *  *   Valores entre 35-40 kg/m² – pessoas com obesidade moderada.
 *  *   Valores > 40kg/m² – pessoas com obesidade grave.
 * Desenvolva um programa em Java que dado o gênero, peso e altura de uma pessoa,
 * compute a categoria que se encontra.
 */

import java.util.Scanner;

public class IMC {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        float peso, altura, IMC;
        int genero;

        System.out.println("Digite o peso em quilos e a altura em metros");
        peso = in.nextFloat();
        altura = in.nextFloat();

        System.out.println("1 pra homi 2 pra muie");
        genero = in.nextInt();

        IMC = peso/(altura*altura);

        if (IMC < 18) {
            System.out.println("Abaixo do peso");
        } else if ((IMC < 24 && genero == 2) || (IMC < 25 && genero == 1)) {
            System.out.println("Peso normal");
        } else if(IMC < 30) {
            System.out.println("Sobrepeso");
        } else if(IMC<35) {
            System.out.println("Obesidade leve");
        } else if (IMC<40) {
            System.out.println("Obesidade moderada");
        } else {
            System.out.println("Obesidade grave");
        }

        in.close();
    }
}
