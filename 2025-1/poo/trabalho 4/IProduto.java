public interface IProduto {
  public String getNome();

  public float getValorBase();

  public EnumTipoProduto getTipo();

  public float getValorComDesconto(IDescontoStrategy descontoStrategy, Pedido pedido, ItemPedido item);
  
  public float getValorComImposto(IImpostoStrategy impostoStrategy);
}
