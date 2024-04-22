/*
 * Leia 3 valores e calcule a média entre os dois maiores
 */

import java.util.Scanner;

public class MediaCarnaval {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int n1, n2, n3, maior1 = 0, maior2 = 0;
    float media = 0;

    System.out.println("Digite 3 valores:");
    n1 = in.nextInt();
    n2 = in.nextInt();
    n3 = in.nextInt();

    if (n1 >= n3 && n2 >= n3) {
      media = (n1 + n2) / 2f;
      maior1 = n1;
      maior2 = n2;
    } else if (n2 >= n1 && n3 >= n1) {
      media = (n2 + n3) / 2f;
      maior1 = n2;
      maior2 = n3;
    } else if (n3 >= n2 && n1 >= n2) {
      media = (n1 + n3) / 2f;
      maior1 = n1;
      maior2 = n3;
    }

    System.out.printf("A média entre %d e %d é %.2f\n", maior1, maior2, media);

    in.close();
  }
}
