package com.command.game.negocio;

public class AttackCommand implements Command {
  private Player player;

  public AttackCommand(Player player) {
    this.player = player;
  }

  @Override
  public void execute() {
    player.atacar();
  }

  @Override
  public String getDescription() {
    return "Atacar";
  }
}