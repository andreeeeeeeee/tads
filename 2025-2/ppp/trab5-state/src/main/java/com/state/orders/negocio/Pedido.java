package com.state.orders.negocio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {
  private final long numero;
  private final String cliente;
  private final double valor;
  private final LocalDateTime dataHora;
  private EstadoPedido estadoAtual;

  public Pedido(long numero, String cliente, double valor) {
    this.numero = numero;
    this.cliente = cliente;
    this.valor = valor;
    this.dataHora = LocalDateTime.now();
    this.estadoAtual = EstadoNovo.INSTANCE;
  }

  public void processarPagamento() {
    System.out.println("\n=== PROCESSANDO PAGAMENTO ===");
    this.estadoAtual = this.estadoAtual.processarPagamento();
  }

  public void enviar() {
    System.out.println("\n=== ENVIANDO PEDIDO ===");
    this.estadoAtual = this.estadoAtual.enviar();
  }

  public void entregar() {
    System.out.println("\n=== ENTREGANDO PEDIDO ===");
    this.estadoAtual = this.estadoAtual.entregar();
  }

  public void cancelar() {
    System.out.println("\n=== CANCELANDO PEDIDO ===");
    this.estadoAtual = this.estadoAtual.cancelar();
  }

  public void exibirPedido() {
    System.out.println("\n" + "=".repeat(50));
    System.out.println("PEDIDO #" + numero);
    System.out.println("Cliente: " + cliente);
    System.out.println("Valor: R$ " + String.format("%.2f", valor));
    System.out.println("Data/Hora: " + dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    System.out.println("" + estadoAtual);
    System.out.println("\nAções disponíveis:");
    String[] acoes = estadoAtual.getAcoesDisponiveis();
    for (int i = 0; i < acoes.length; i++) 
      System.out.println("   " + (i + 1) + ". " + acoes[i]);
    System.out.println("=".repeat(50));
  }

  public long getNumero() {
    return numero;
  }

  public String getCliente() {
    return cliente;
  }

  public double getValor() {
    return valor;
  }

  public LocalDateTime getDataHora() {
    return dataHora;
  }

  public EstadoPedido getEstadoAtual() {
    return estadoAtual;
  }

  public String getNomeEstadoAtual() {
    return estadoAtual.getNomeEstado();
  }
}
