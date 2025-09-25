package com.state.orders.negocio;

public interface EstadoPedido {
  EstadoPedido processarPagamento();

  EstadoPedido enviar();

  EstadoPedido entregar();

  EstadoPedido cancelar();

  String getNomeEstado();

  String[] getAcoesDisponiveis();
}
