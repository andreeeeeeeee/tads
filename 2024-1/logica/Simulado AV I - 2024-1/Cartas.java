/*
 * 6.
 * Os alunos do IF criaram um jogo de sorte com cartas, cada jogador comprava 2 cartas e
 * faziam suas apostas, ganhava o jogador que tivesse a pontuação mais alta. As cartas que
 * compunham o jogo eram um baralho tradicional de 52 cartas, sem os coringas, com o
 * números de 1 ao 13 e 4 naipes disponíveis, copas, espadas, ouros, paus. Dada a jogada de
 * dois jogadores, indicar qual a pontuação de cada jogador e quem é o vencedor da rodada.
 * Números sequenciais de mesmo naipe:          multiplica-se o valor das cartas
 * Números sequenciais de naipe diferente:      somam-se os valores
 * Números não sequenciais de mesmo naipe:      vale a carta de maior valor
 * Números não sequenciais de naipe diferente:  subtrai-se a carta de menor valor da carta de maior valor
 * Números iguais:                              número ao quadrado

 */

import java.util.Scanner;

public class Cartas {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int carta1Jog1 = in.nextInt();
    String naipe1Jog1 = in.next();
    int carta2Jog1 = in.nextInt();
    String naipe2Jog1 = in.next();
    int carta1Jog2 = in.nextInt();
    String naipe1Jog2 = in.next();
    int carta2Jog2 = in.nextInt();
    String naipe2Jog2 = in.next();
    int pontosJog1 = 0, pontosJog2 = 0;

    in.close();

    if (carta1Jog1 == carta2Jog1) {
      pontosJog1 = carta1Jog1 * carta2Jog1;
    } else if (naipe1Jog1.equals(naipe2Jog1)) {
      if (carta1Jog1 == carta2Jog1 + 1 || carta1Jog1 == carta2Jog1 - 1) {
        pontosJog1 = carta1Jog1 * carta2Jog1;
      } else {
        pontosJog1 = Math.max(carta1Jog1, carta2Jog1);
      }
    } else {
      if (carta1Jog1 == carta2Jog1 + 1 || carta1Jog1 == carta2Jog1 - 1) {
        pontosJog1 = carta1Jog1 + carta2Jog1;
      } else {
        pontosJog1 = Math.abs((carta1Jog1 - carta2Jog1));
      }
    }

    if (carta1Jog2 == carta2Jog2) {
      pontosJog2 = carta1Jog2 * carta2Jog2;
    } else if (naipe1Jog2.equals(naipe2Jog2)) {
      if (carta1Jog2 == carta2Jog2 + 1 || carta1Jog2 == carta2Jog2 - 1) {
        pontosJog2 = carta1Jog2 * carta2Jog2;
      } else {
        pontosJog2 = Math.max(carta1Jog2, carta2Jog2);
      }
    } else {
      if (carta1Jog2 == carta2Jog2 + 1 || carta1Jog2 == carta2Jog2 - 1) {
        pontosJog2 = carta1Jog2 + carta2Jog2;
      } else {
        pontosJog2 = Math.abs((carta1Jog2 - carta2Jog2));
      }
    }

    if (pontosJog1 == pontosJog2) {
      System.out.printf("Empate, ambos jogadores fizeram %d pontos\n", pontosJog1);
    } else if (pontosJog1 > pontosJog2) {
      System.out.printf("O jogador 1 venceu com %d pontos contra os %d pontos do jogador 2.\n", pontosJog1, pontosJog2);
    } else {
      System.out.printf("O jogador 2 venceu com %d pontos contra os %d pontos do jogador 1.\n", pontosJog2, pontosJog1);
    }
  }
}
