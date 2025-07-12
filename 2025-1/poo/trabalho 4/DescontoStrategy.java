public abstract class DescontoStrategy implements IDescontoStrategy {
  protected  final float percentualDesconto;

  public DescontoStrategy(float percentualDesconto) {
    this.percentualDesconto = percentualDesconto;
  }

  public float getPercentualDesconto() {
    return this.percentualDesconto;
  }
}
