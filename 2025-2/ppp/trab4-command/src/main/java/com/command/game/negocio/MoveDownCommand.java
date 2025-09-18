package com.command.game.negocio;

public class MoveDownCommand implements Command {
  private Player player;

  public MoveDownCommand(Player player) {
    this.player = player;
  }

  @Override
  public void execute() {
    player.moverBaixo();
  }

  @Override
  public String getDescription() {
    return "Mover para baixo";
  }
}