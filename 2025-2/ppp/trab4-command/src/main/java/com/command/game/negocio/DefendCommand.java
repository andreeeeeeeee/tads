package com.command.game.negocio;

public class DefendCommand implements Command {
  private Player player;

  public DefendCommand(Player player) {
    this.player = player;
  }

  @Override
  public void execute() {
    if (player.isDefendendo()) {
      player.pararDefender();
      return;
    }
    player.defender();
  }

  @Override
  public String getDescription() {
    return "Defender";
  }
}