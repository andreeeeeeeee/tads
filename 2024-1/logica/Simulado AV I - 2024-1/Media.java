/*
 * 3.
 * Escreva um algoritmo que leia as notas das duas avaliações normais e a nota da avaliação optativa.
 * Caso o aluno não tenha feito a optativa deve ser fornecido o valor –1.
 * Calcular a média do semestre considerando que a prova optativa substitui a nota mais baixa entre as
 * duas primeiras avaliações.
 * Escrever a média e mensagens que indiquem se o aluno foi aprovado, reprovado ou está em exame,
 * de acordo com as informações abaixo:
 *  *   Aprovado : media >= 6.0
 *  *   Reprovado: media < 3.0
 *  *   Exame : media >= 3.0 e < 6.0
 */

import java.util.Scanner;

public class Media {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    float n1, n2, nOpta, media;

    System.out.println(
        "Digite as notas das duas avaliações e da optativa, caso seja realizada. Caso contrário, digite o valor -1.");
    n1 = in.nextFloat();
    n2 = in.nextFloat();
    nOpta = in.nextFloat();

    media = (n1 + n2) / 2;

    if (nOpta > -1) {
      float menor = Math.min(n1, n2);
      float maior = Math.max(n1, n2);
      if (nOpta > menor) {
        media = (maior + nOpta) / 2;
      }
    }

    if (media >= 6) {
      System.out.println("Aprovado");
    } else if (media < 3) {
      System.out.println("Reprovado");
    } else {
      System.out.println("Exame");
    }

    in.close();
  }
}
