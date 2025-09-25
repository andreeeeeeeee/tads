package com.state.orders.negocio;

public class EstadoEntregue implements EstadoPedido {

  public static final EstadoEntregue INSTANCE = new EstadoEntregue();

  private EstadoEntregue() {
  }

  @Override
  public EstadoPedido processarPagamento() {
    System.out.println("Pedido já foi entregue!");
    return this;
  }

  @Override
  public EstadoPedido enviar() {
    System.out.println("Pedido já foi entregue!");
    return this;
  }

  @Override
  public EstadoPedido entregar() {
    System.out.println("Pedido já foi entregue!");
    return this;
  }

  @Override
  public EstadoPedido cancelar() {
    System.out.println("Não é possível cancelar um pedido já entregue!");
    return this;
  }

  @Override
  public String getNomeEstado() {
    return Estados.ENTREGUE.getNome();
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
