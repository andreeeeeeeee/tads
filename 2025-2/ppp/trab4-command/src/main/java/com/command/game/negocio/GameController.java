package com.command.game.negocio;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class GameController {
  private Map<String, Command> keyBindings;
  private List<Command> commandHistory;
  private Player player;

  public GameController(Player player) {
    this.player = player;
    this.keyBindings = new HashMap<>();
    this.commandHistory = new ArrayList<>();
    inicializarControles();
  }

  private void inicializarControles() {
    keyBindings.put("W", new MoveUpCommand(player));
    keyBindings.put("S", new MoveDownCommand(player));
    keyBindings.put("A", new MoveLeftCommand(player));
    keyBindings.put("D", new MoveRightCommand(player));

    keyBindings.put("SPACE", new AttackCommand(player));
    keyBindings.put("SHIFT", new DefendCommand(player));
    keyBindings.put("E", new UseItemCommand(player));

    keyBindings.put("Q", new NoCommand());
    keyBindings.put("R", new NoCommand());
    keyBindings.put("T", new NoCommand());
  }

  public void processInput(String key) {
    Command command = keyBindings.get(key.toUpperCase());

    if (command != null) {
      System.out.printf("[INPUT] Tecla %s pressionada - %s%n", key, command.getDescription());
      executeCommand(command);
    } else
      System.out.printf("[INPUT] Tecla %s não mapeada%n", key);
  }

  private void executeCommand(Command command) {
    command.execute();

    if (!(command instanceof NoCommand))
      commandHistory.add(command);
  }

  public void showControls() {
    System.out.println("=== CONTROLES DO JOGO ===");
    for (Map.Entry<String, Command> entry : keyBindings.entrySet())
      if (!(entry.getValue() instanceof NoCommand))
        System.out.printf("%s: %s%n", entry.getKey(), entry.getValue().getDescription());

    System.out.println("========================");
  }

  public void showCommandHistory() {
    System.out.println("=== HISTÓRICO DE COMANDOS ===");
    if (commandHistory.isEmpty()) {
      System.out.println("Nenhum comando executado");
      System.out.println("============================");
      return;
    }

    for (int i = 0; i < commandHistory.size(); i++) {
      Command command = commandHistory.get(i);
      System.out.printf("%d. %s%n", (i + 1), command.getDescription());
    }

    System.out.println("============================");
  }
}