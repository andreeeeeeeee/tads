
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

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Date getDataPedido() {
    return dataPedido;
  }

  public void setDataPedido(Date dataPedido) {
    this.dataPedido = dataPedido;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public List<ItemPedido> getItens() {
    return itens;
  }

  public void setItens(List<ItemPedido> itens) {
    this.itens = itens;
  }

  public float getValorTotalBruto() {
    return valorTotalBruto;
  }

  public void setValorTotalBruto(float valorTotalBruto) {
    this.valorTotalBruto = valorTotalBruto;
  }

  public float getValorTotalDescontos() {
    return valorTotalDescontos;
  }

  public void setValorTotalDescontos(float valorTotalDescontos) {
    this.valorTotalDescontos = valorTotalDescontos;
  }

  public float getValorTotalImpostos() {
    return valorTotalImpostos;
  }

  public void setValorTotalImpostos(float valorTotalImpostos) {
    this.valorTotalImpostos = valorTotalImpostos;
  }

  public float getValorTotalLiquido() {
    return valorTotalLiquido;
  }

  public void setValorTotalLiquido(float valorTotalLiquido) {
    this.valorTotalLiquido = valorTotalLiquido;
  }

  public void adicionarItem(ItemPedido item) {
    itens.add(item);
  }

  public void calcularTotais() {
  }

}
