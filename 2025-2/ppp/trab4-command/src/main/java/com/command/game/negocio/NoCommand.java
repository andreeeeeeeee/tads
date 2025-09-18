package com.command.game.negocio;

public class NoCommand implements Command {

  @Override
  public void execute() {
  }

  @Override
  public String getDescription() {
    return "Nenhum comando";
  }
}