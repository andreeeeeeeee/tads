
import java.util.Calendar;

public class DescontoBlackFridayStrategy extends DescontoStrategy {

  public DescontoBlackFridayStrategy(float percentualDesconto) {
    super(percentualDesconto);
  }

  @Override
  public float calcularDesconto(Pedido pedido, ItemPedido item, IProduto produto) {
    if (!isBlackFriday(pedido)) {
      return 0;
    }

    float desconto = produto.getValorBase() * this.percentualDesconto / 100;
    return desconto;
  }

  private boolean isBlackFriday(Pedido pedido) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(pedido.getDataPedido());
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);

    return month == Calendar.NOVEMBER && (day >= 22 && day <= 28) && cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
  }
}
