package com.state.orders.negocio;

public class EstadoCancelado implements EstadoPedido {

  public static final EstadoCancelado INSTANCE = new EstadoCancelado();

  private EstadoCancelado() {
  }

  @Override
  public EstadoPedido processarPagamento() {
    System.out.println("Não é possível processar pagamento de um pedido cancelado!");
    return this;
  }

  @Override
  public EstadoPedido enviar() {
    System.out.println("Não é possível enviar um pedido cancelado!");
    return this;
  }

  @Override
  public EstadoPedido entregar() {
    System.out.println("Não é possível entregar um pedido cancelado!");
    return this;
  }

  @Override
  public EstadoPedido cancelar() {
    System.out.println("Pedido já está cancelado!");
    return this;
  }

  @Override
  public String getNomeEstado() {
    return Estados.CANCELADO.getNome();
  }

  @Override
  public String[] getAcoesDisponiveis() {
    return new String[] { "Nenhuma ação disponível" };
  }

  @Override
  public String toString() {
    return "Estado: " + getNomeEstado() + " (Estado Final)";
  }
}
