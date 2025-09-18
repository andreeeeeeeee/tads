package com.command.game.negocio;

public class MoveUpCommand implements Command {
  private Player player;

  public MoveUpCommand(Player player) {
    this.player = player;
  }

  @Override
  public void execute() {
    player.moverCima();
  }

  @Override
  public String getDescription() {
    return "Mover para cima";
  }
}