package com.command.game.negocio;

public class Player {
  private int x, y;
  private int vida;
  private int mana;
  private int nivel;
  private boolean defendendo;
  private int itens;

  public Player() {
    this.x = 0;
    this.y = 0;
    this.vida = 100;
    this.mana = 50;
    this.nivel = 1;
    this.defendendo = false;
    this.itens = 3;
  }

  public void moverCima() {
    this.y++;
    System.out.printf("Jogador moveu para cima. Nova posição: (%d, %d)%n", x, y);
  }

  public void moverBaixo() {
    this.y--;
    System.out.printf("Jogador moveu para baixo. Nova posição: (%d, %d)%n", x, y);
  }

  public void moverEsquerda() {
    this.x--;
    System.out.printf("Jogador moveu para esquerda. Nova posição: (%d, %d)%n", x, y);
  }

  public void moverDireita() {
    this.x++;
    System.out.printf("Jogador moveu para direita. Nova posição: (%d, %d)%n", x, y);
  }

  public void atacar() {
    if (defendendo)
      pararDefender();

    if (mana >= 10) {
      mana -= 10;
      System.out.printf("Jogador atacou! Mana restante: %d%n", mana);
    } else
      System.out.println("Mana insuficiente para atacar!");
  }

  public void defender() {
    this.defendendo = true;
    System.out.println("Jogador está defendendo!");
  }

  public void pararDefender() {
    this.defendendo = false;
    System.out.println("Jogador parou de defender!");
  }

  public void usarItem() {
    if (itens > 0) {
      itens--;
      vida = Math.min(vida + 20, 100);
      System.out.printf("Jogador usou um item! Vida: %d, Itens restantes: %d%n", vida, itens);
    } else
      System.out.println("Nenhum item disponível!");
  }

  public void mostrarStatus() {
    System.out.printf("=== STATUS DO JOGADOR ===%n");
    System.out.printf("Posição: (%d, %d)%n", x, y);
    System.out.printf("Vida: %d%n", vida);
    System.out.printf("Mana: %d%n", mana);
    System.out.printf("Nível: %d%n", nivel);
    System.out.printf("Defendendo: %s%n", defendendo ? "Sim" : "Não");
    System.out.printf("Itens: %d%n", itens);
    System.out.println("========================");
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getVida() {
    return vida;
  }

  public int getMana() {
    return mana;
  }

  public int getNivel() {
    return nivel;
  }

  public boolean isDefendendo() {
    return defendendo;
  }

  public int getItens() {
    return itens;
  }
}