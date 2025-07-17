public class CalculadoraPedido {
  private final IDescontoStrategy descontoStrategy;
  private final IImpostoStrategyFactory impostoStrategyFactory;

  public CalculadoraPedido(IDescontoStrategy descontoStrategy) {
    this.descontoStrategy = descontoStrategy;
    this.impostoStrategyFactory = new ImpostoStrategyFactory();
  }

  public CalculadoraPedido(IDescontoStrategy descontoStrategy, IImpostoStrategyFactory impostoStrategyFactory) {
    this.descontoStrategy = descontoStrategy;
    this.impostoStrategyFactory = impostoStrategyFactory;
  }

  public void processarPedido(Pedido pedido) {
    for (ItemPedido item : pedido.getItens()) {
      IProduto produto = item.getProduto();

      float valorUnitario = produto.getValorBase();
      item.setValorUnitario(valorUnitario);

      int quantidade = item.getQuantidade();
      float valorTotalItem = valorUnitario * quantidade;
      item.setValorTotalItem(valorTotalItem);

      float descontoPorUnidade = 0;
      if (this.descontoStrategy != null)
        descontoPorUnidade = this.descontoStrategy.calcularDesconto(pedido, item, produto);

      float descontoTotal = descontoPorUnidade * quantidade;
      item.setDescontoAplicado(descontoTotal);

      IImpostoStrategy impostoStrategy = impostoStrategyFactory.createImpostoStrategy(produto.getTipo(), 10);
      float impostoPorUnidade = 0;
      if (impostoStrategy != null)
        impostoPorUnidade = impostoStrategy.calcularImposto(produto, valorUnitario);

      float impostoTotal = impostoPorUnidade * quantidade;
      item.setImpostoAplicado(impostoTotal);

      float valorLiquidoItem = valorTotalItem - descontoTotal + impostoTotal;
      item.setValorLiquidoItem(valorLiquidoItem);
    }

    pedido.calcularTotais();
  }
}
