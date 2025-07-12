public abstract class Produto implements IProduto {
  private final String nome;
  private final EnumTipoProduto tipo;
  private final float valorBase;

  public Produto(String nome, EnumTipoProduto tipo, float valorBase) {
    this.nome = nome;
    this.tipo = tipo;
    this.valorBase = valorBase;
  }

  @Override
  public String getNome() {
    return this.nome;
  }

  @Override
  public float getValorBase() {
    return this.valorBase;
  }

  @Override
  public EnumTipoProduto getTipo() {
    return this.tipo;
  }

  @Override
  public float getValorComDesconto(IDescontoStrategy descontoStrategy, Pedido pedido, ItemPedido item) {
    if (descontoStrategy == null) {
      return this.valorBase;
    }
    return this.valorBase - descontoStrategy.calcularDesconto(pedido, item, this);
  }

  @Override
  public float getValorComImposto(IImpostoStrategy impostoStrategy) {
    if (impostoStrategy == null) {
      return this.valorBase;
    }
    return this.valorBase + impostoStrategy.calcularImposto(this, this.valorBase);
  }

}
