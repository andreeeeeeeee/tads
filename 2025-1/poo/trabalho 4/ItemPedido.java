public class ItemPedido {
  private IProduto produto;
  private int quantidade;
  private float valorUnitario;
  private float valorTotalItem;
  private float descontoAplicado;
  private float impostoAplicado;
  private float valorLiquidoItem;

  public ItemPedido(IProduto produto, int quantidade) {
    this.produto = produto;
    this.quantidade = quantidade;
    this.valorUnitario = produto.getValorBase();
    this.valorTotalItem = this.valorUnitario * quantidade;
    this.descontoAplicado = 0;
    this.impostoAplicado = 0;
    this.valorLiquidoItem = this.valorTotalItem - this.descontoAplicado + this.impostoAplicado;
  }

  public IProduto getProduto() {
    return this.produto;
  }

  public void setProduto(IProduto produto) {
    this.produto = produto;
  }

  public int getQuantidade() {
    return this.quantidade;
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }

  public float getValorUnitario() {
    return this.valorUnitario;
  }

  public void setValorUnitario(float valorUnitario) {
    this.valorUnitario = valorUnitario;
  }

  public float getValorTotalItem() {
    return this.valorTotalItem;
  }

  public void setValorTotalItem(float valorTotalItem) {
    this.valorTotalItem = valorTotalItem;
  }

  public float getDescontoAplicado() {
    return this.descontoAplicado;
  }

  public void setDescontoAplicado(float descontoAplicado) {
    this.descontoAplicado = descontoAplicado;
  }

  public float getImpostoAplicado() {
    return this.impostoAplicado;
  }

  public void setImpostoAplicado(float impostoAplicado) {
    this.impostoAplicado = impostoAplicado;
  }

  public float getValorLiquidoItem() {
    return this.valorLiquidoItem;
  }

  public void setValorLiquidoItem(float valorLiquidoItem) {
    this.valorLiquidoItem = valorLiquidoItem;
  }
}
