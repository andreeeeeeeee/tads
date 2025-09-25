package com.state.orders.negocio;

public class EstadoPago implements EstadoPedido {

  public static final EstadoPago INSTANCE = new EstadoPago();

  private EstadoPago() {
  }

  @Override
  public EstadoPedido processarPagamento() {
    System.out.println("Pedido já foi pago!");
    return this;
  }

  @Override
  public EstadoPedido enviar() {
    System.out.println("Pedido enviado para entrega!");
    return EstadoEnviado.INSTANCE;
  }

  @Override
  public EstadoPedido entregar() {
    System.out.println("Não é possível entregar um pedido que não foi enviado!");
    return this;
  }

  @Override
  public EstadoPedido cancelar() {
    System.out.println("Pedido cancelado! Estorno será processado.");
    return EstadoCancelado.INSTANCE;
  }

  @Override
  public String getNomeEstado() {
    return Estados.PAGO.getNome();
  }

  @Override
  public String[] getAcoesDisponiveis() {
    return new String[] { "Enviar", "Cancelar" };
  }

  @Override
  public String toString() {
    return "Estado: " + getNomeEstado();
  }
}
