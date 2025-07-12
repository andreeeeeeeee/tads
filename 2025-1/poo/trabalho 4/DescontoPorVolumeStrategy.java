public class DescontoPorVolumeStrategy extends DescontoStrategy {
  private final int quantidadeMinima;

  public DescontoPorVolumeStrategy(float percentualDesconto, int quantidadeMinima) {
    super(percentualDesconto);
    this.quantidadeMinima = quantidadeMinima;
  }

  @Override
  public float calcularDesconto(Pedido pedido, ItemPedido item, IProduto produto) {
    if (item.getQuantidade() < this.quantidadeMinima) {
      return 0;
    }

    float desconto = produto.getValorBase() * this.percentualDesconto / 100;
    return desconto;
  }

}
