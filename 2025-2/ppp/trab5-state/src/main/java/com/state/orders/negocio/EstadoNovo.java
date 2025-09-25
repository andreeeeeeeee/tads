package com.state.orders.negocio;

public class EstadoNovo implements EstadoPedido {

  public static final EstadoNovo INSTANCE = new EstadoNovo();

  private EstadoNovo() {
  }

  @Override
  public EstadoPedido processarPagamento() {
    System.out.println("Pagamento processado com sucesso!");
    return EstadoPago.INSTANCE;
  }

  @Override
  public EstadoPedido enviar() {
    System.out.println("Não é possível enviar um pedido não pago!");
    return this;
  }

  @Override
  public EstadoPedido entregar() {
    System.out.println("Não é possível entregar um pedido não pago!");
    return this;
  }

  @Override
  public EstadoPedido cancelar() {
    System.out.println("Pedido cancelado!");
    return EstadoCancelado.INSTANCE;
  }

  @Override
  public String getNomeEstado() {
    return Estados.NOVO.getNome();
  }

  @Override
  public String[] getAcoesDisponiveis() {
    return new String[] { "Processar Pagamento", "Cancelar" };
  }

  @Override
  public String toString() {
    return "Estado: " + getNomeEstado();
  }
}
