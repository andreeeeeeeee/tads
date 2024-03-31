/*
 * 5.
 * Distância Entre Dois Pontos:
 * Peça ao usuário para inserir as coordenadas x e
 * y de dois pontos. Calcule e imprima a distância
 * entre esses dois pontos. A fórmula da distância
 * é sqrt((x2 - x1)^2 + (y2 - y1)^2).
 */

import java.util.Scanner;

public class Distancia {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        double x1, x2, y1, y2, distancia, deltaX, deltaY;

        System.out.println("Digite as coordenadas do primeiro ponto:");
        x1 = in.nextInt();
        y1 = in.nextInt();
        System.out.println("Digite as coordenadas do segundo ponto:");
        x2 = in.nextInt();
        y2 = in.nextInt();

        deltaX = Math.pow((x2-x1), 2);
        deltaY = Math.pow((y2-y1), 2);

        distancia = Math.sqrt((deltaX + deltaY));

        System.out.printf("A distância entre P(%.0f, %.0f) e Q(%.0f, %.0f) é %.2f\n", x1, y1, x2, y2, distancia);

        in.close();
    }
}