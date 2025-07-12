public interface IDescontoStrategy {
  public float calcularDesconto(Pedido pedido, ItemPedido item, IProduto produto);
}
