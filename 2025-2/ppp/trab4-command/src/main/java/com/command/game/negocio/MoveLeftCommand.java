package com.command.game.negocio;

public class MoveLeftCommand implements Command {
  private Player player;

  public MoveLeftCommand(Player player) {
    this.player = player;
  }

  @Override
  public void execute() {
    player.moverEsquerda();
  }

  @Override
  public String getDescription() {
    return "Mover para esquerda";
  }
}