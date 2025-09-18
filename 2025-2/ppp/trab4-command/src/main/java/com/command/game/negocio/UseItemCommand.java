package com.command.game.negocio;

public class UseItemCommand implements Command {
  private Player player;

  public UseItemCommand(Player player) {
    this.player = player;
  }

  @Override
  public void execute() {
    player.usarItem();
  }

  @Override
  public String getDescription() {
    return "Usar item";
  }
}