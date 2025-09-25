package com.state.orders.negocio;

public enum Estados {
  NOVO("NOVO"),
  PAGO("PAGO"),
  ENVIADO("ENVIADO"),
  ENTREGUE("ENTREGUE"),
  CANCELADO("CANCELADO");

  private final String nome;

  Estados(String nome) {
    this.nome = nome;
  }

  public String getNome() {
    return nome;
  }
}
