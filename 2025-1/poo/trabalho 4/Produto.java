public abstract class Produto implements IProduto {
  private String nome;
  private EnumTipoProduto tipo;
  private float valorBase;
  private float valorComDesconto;
  private float valorComImposto;

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
    return this.valorComDesconto;
  }

  @Override
  public float getValorComImposto(IImpostoStrategy impostoStrategy) {
    return this.valorComImposto;
  }

}
