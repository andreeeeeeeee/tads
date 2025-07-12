public class ImpostoProdutoDigitalStrategy implements IImpostoStrategy {
  private final float aliquota;

  public ImpostoProdutoDigitalStrategy(float aliquota) {
    this.aliquota = aliquota;
  }

  public float getAliquota() {
    return this.aliquota;
  }

  @Override
  public float calcularImposto(IProduto produto, float valorBase) {
    if (!(produto instanceof ProdutoDigital)) {
      throw new IllegalArgumentException("Produto não é do tipo ProdutoDigital");
    }

    return valorBase * this.aliquota;
  }

}
