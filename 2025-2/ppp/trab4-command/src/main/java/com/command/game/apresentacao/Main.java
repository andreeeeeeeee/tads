package com.command.game.apresentacao;

import com.command.game.negocio.GameController;
import com.command.game.negocio.Player;

public class Main {
  public static void main(String[] args) {
    System.out.println();

    Player player = new Player();

    GameController gameController = new GameController(player);

    gameController.showControls();
    System.out.println();

    player.mostrarStatus();
    System.out.println();

    System.out.println("Simulando sequência de movimentos e ações:");
    gameController.processInput("W");
    gameController.processInput("W");
    gameController.processInput("D");
    gameController.processInput("D");
    gameController.processInput("SPACE");
    gameController.processInput("E");
    gameController.processInput("SHIFT");

    System.out.println();
    player.mostrarStatus();

    System.out.println();
    gameController.processInput("X");

    System.out.println();
    gameController.showCommandHistory();

    System.out.println();
  }
}