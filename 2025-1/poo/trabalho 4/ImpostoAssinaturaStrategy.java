public class ImpostoAssinaturaStrategy implements IImpostoStrategy {
  private final float aliquota;

  public ImpostoAssinaturaStrategy(float aliquota) {
    this.aliquota = aliquota;
  }

  public float getAliquota() {
    return this.aliquota;
  }

  @Override
  public float calcularImposto(IProduto produto, float valorBase) {
    if (!(produto instanceof Assinatura))
      throw new IllegalArgumentException("Produto não é do tipo Assinatura");

    return valorBase * (this.aliquota / 100);
  }
}
