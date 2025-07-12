
import java.util.Calendar;

public class DescontoMesAniversarioStrategy extends DescontoStrategy {

  public DescontoMesAniversarioStrategy(float percentualDesconto) {
    super(percentualDesconto);
  }

  @Override
  public float calcularDesconto(Pedido pedido, ItemPedido item, IProduto produto) {
    if (!isAniversarioMes(pedido)) {
      return 0;
    }

    float desconto = produto.getValorBase() * this.percentualDesconto / 100;
    return desconto;
  }

  private boolean isAniversarioMes(Pedido pedido) {
    Cliente cliente = pedido.getCliente();

    Calendar calNascimento = Calendar.getInstance();
    calNascimento.setTime(cliente.getDataNascimento());
    int mesNascimento = calNascimento.get(Calendar.MONTH);

    Calendar calPedido = Calendar.getInstance();
    calPedido.setTime(pedido.getDataPedido());
    int mesPedido = calPedido.get(Calendar.MONTH);

    return mesNascimento == mesPedido;
  }
}
