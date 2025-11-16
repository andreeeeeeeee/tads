package com.prototype.negocio;

public enum ECategoria {
  ENTRADA("Entrada"),
  PRATO_PRINCIPAL("Prato Principal"),
  SOBREMESA("Sobremesa"),
  BEBIDA("Bebida");

  private final String descricao;

  ECategoria(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }
}
