public class ImpostoProdutoFisicoStrategy implements IImpostoStrategy {
  private final float aliquotaNacional;

  public ImpostoProdutoFisicoStrategy(float aliquotaNacional) {
    this.aliquotaNacional = aliquotaNacional;
  }

  @Override
  public float calcularImposto(IProduto produto, float valorBase) {
    return valorBase * this.aliquotaNacional / 100;
  }

}
