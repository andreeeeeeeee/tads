package com.state.orders.negocio;

public class EstadoEnviado implements EstadoPedido {

  public static final EstadoEnviado INSTANCE = new EstadoEnviado();

  private EstadoEnviado() {
  }

  @Override
  public EstadoPedido processarPagamento() {
    System.out.println("Pedido já foi pago anteriormente!");
    return this;
  }

  @Override
  public EstadoPedido enviar() {
    System.out.println("Pedido já foi enviado!");
    return this;
  }

  @Override
  public EstadoPedido entregar() {
    System.out.println("Pedido entregue com sucesso!");
    return EstadoEntregue.INSTANCE;
  }

  @Override
  public EstadoPedido cancelar() {
    System.out.println("Não é possível cancelar um pedido já enviado!");
    return this;
  }

  @Override
  public String getNomeEstado() {
    return Estados.ENVIADO.getNome();
  }

  @Override
  public String[] getAcoesDisponiveis() {
    return new String[] { "Entregar" };
  }

  @Override
  public String toString() {
    return "Estado: " + getNomeEstado();
  }
}
