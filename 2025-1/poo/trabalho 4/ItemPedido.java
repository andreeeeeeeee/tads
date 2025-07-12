

public class ItemPedido {
  private IProduto produto;
  private int quantidade;
  private float valorUnitario;
  private float valorTotalItem;
  private float descontoAplicado;
  private float impostoAplicado;
  private float valorLiquidoItem;

    public IProduto getProduto() {
        return produto;
    }

    public void setProduto(IProduto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public float getValorTotalItem() {
        return valorTotalItem;
    }

    public void setValorTotalItem(float valorTotalItem) {
        this.valorTotalItem = valorTotalItem;
    }

    public float getDescontoAplicado() {
        return descontoAplicado;
    }

    public void setDescontoAplicado(float descontoAplicado) {
        this.descontoAplicado = descontoAplicado;
    }

    public float getImpostoAplicado() {
        return impostoAplicado;
    }

    public void setImpostoAplicado(float impostoAplicado) {
        this.impostoAplicado = impostoAplicado;
    }

    public float getValorLiquidoItem() {
        return valorLiquidoItem;
    }

    public void setValorLiquidoItem(float valorLiquidoItem) {
        this.valorLiquidoItem = valorLiquidoItem;
    }
}
