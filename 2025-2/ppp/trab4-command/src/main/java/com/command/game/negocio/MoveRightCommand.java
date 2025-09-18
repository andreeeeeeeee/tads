package com.command.game.negocio;

public class MoveRightCommand implements Command {
  private Player player;

  public MoveRightCommand(Player player) {
    this.player = player;
  }

  @Override
  public void execute() {
    player.moverDireita();
  }

  @Override
  public String getDescription() {
    return "Mover para direita";
  }
}