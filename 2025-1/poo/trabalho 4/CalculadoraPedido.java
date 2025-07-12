public class CalculadoraPedido {
  private final IDescontoStrategy descontoStrategy;
  private final ImpostoStrategyFactory impostoStrategyFactory;

  public CalculadoraPedido(IDescontoStrategy descontoStrategy) {
    this.descontoStrategy = descontoStrategy;
    this.impostoStrategyFactory = new ImpostoStrategyFactory();
  }

  public void processarPedido(Pedido pedido) {
    for (ItemPedido item : pedido.getItens()) {
      IProduto produto = item.getProduto();

      float valorUnitario = produto.getValorBase();
      item.setValorUnitario(valorUnitario);

      int quantidade = item.getQuantidade();
      float valorTotalItem = valorUnitario * quantidade;
      item.setValorTotalItem(valorTotalItem);

      float valorComDesconto = produto.getValorComDesconto(this.descontoStrategy, pedido, item);
      float descontoAplicado = valorUnitario - valorComDesconto;
      item.setDescontoAplicado(descontoAplicado);

      IImpostoStrategy impostoStrategy = impostoStrategyFactory.createImpostoStrategy(produto.getTipo(), 20);
      float valorComImposto = produto.getValorComImposto(impostoStrategy);
      float impostoAplicado = valorComImposto - valorUnitario;
      item.setImpostoAplicado(impostoAplicado);

      float valorLiquidoItem = valorTotalItem - descontoAplicado + impostoAplicado;
      item.setValorLiquidoItem(valorLiquidoItem);
    }

    pedido.calcularTotais();
  }
}
