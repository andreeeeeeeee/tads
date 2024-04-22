/*
 * 5.
 * DADO 5 VALORES, INDIQUE SE ELES FORMAM UMA SEQUENCIA.
 * CONSIDERAREMOS SEQUENCIA QUANDO A DIFERENÇA ENTRE O VALOR E O PROXIMO SE MANTEM. (PA)
 */

import java.util.Scanner;

public class PA {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int i = 0, termo = 0, razao = 0, ultimo = 0;
    boolean paOuNao = true;

    while (i < 5) {
      ultimo = termo;
      i++;
      System.out.printf("Digite o %d° valor: ", i);
      termo = in.nextInt();
      if (i <= 2)
        razao = ultimo - termo;
      else if (ultimo - termo != razao) {
        paOuNao = false;
        break;
      }
    }

    if (paOuNao)
      System.out.println("PA");
    else
      System.out.println("Não é PA");

    in.close();
  }
}
