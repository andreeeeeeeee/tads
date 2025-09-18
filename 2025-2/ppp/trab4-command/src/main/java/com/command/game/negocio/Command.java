package com.command.game.negocio;

public interface Command {
  void execute();

  String getDescription();
}