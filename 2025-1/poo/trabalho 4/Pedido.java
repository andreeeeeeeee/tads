
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Pedido {
  private UUID id;
  private Date dataPedido;
  private Cliente cliente;
  private List<ItemPedido> itens;
  private float valorTotalBruto;
  private float valorTotalDescontos;
  private float valorTotalImpostos;
  private float valorTotalLiquido;

  public Pedido(UUID id, Date dataPedido, Cliente cliente) {
    this.id = id;
    this.dataPedido = dataPedido;
    this.cliente = cliente;
    this.itens = new ArrayList<>();
    this.valorTotalBruto = 0.0f;
    this.valorTotalDescontos = 0.0f;
    this.valorTotalImpostos = 0.0f;
    this.valorTotalLiquido = 0.0f;
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Date getDataPedido() {
    return this.dataPedido;
  }

  public void setDataPedido(Date dataPedido) {
    this.dataPedido = dataPedido;
  }

  public Cliente getCliente() {
    return this.cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public List<ItemPedido> getItens() {
    return this.itens;
  }

  public void setItens(List<ItemPedido> itens) {
    this.itens = itens;
  }

  public float getValorTotalBruto() {
    return this.valorTotalBruto;
  }

  public void setValorTotalBruto(float valorTotalBruto) {
    this.valorTotalBruto = valorTotalBruto;
  }

  public float getValorTotalDescontos() {
    return this.valorTotalDescontos;
  }

  public void setValorTotalDescontos(float valorTotalDescontos) {
    this.valorTotalDescontos = valorTotalDescontos;
  }

  public float getValorTotalImpostos() {
    return this.valorTotalImpostos;
  }

  public void setValorTotalImpostos(float valorTotalImpostos) {
    this.valorTotalImpostos = valorTotalImpostos;
  }

  public float getValorTotalLiquido() {
    return this.valorTotalLiquido;
  }

  public void setValorTotalLiquido(float valorTotalLiquido) {
    this.valorTotalLiquido = valorTotalLiquido;
  }

  public void adicionarItem(ItemPedido item) {
    this.itens.add(item);
  }

  public void calcularTotais() {
    this.valorTotalBruto = 0.0f;
    this.valorTotalDescontos = 0.0f;
    this.valorTotalImpostos = 0.0f;
    this.valorTotalLiquido = 0.0f;

    for (ItemPedido item : this.itens) {
      this.valorTotalBruto += item.getValorTotalItem();
      this.valorTotalDescontos += item.getDescontoAplicado();
      this.valorTotalImpostos += item.getImpostoAplicado();
      this.valorTotalLiquido += item.getValorLiquidoItem();
    }
  }

}
