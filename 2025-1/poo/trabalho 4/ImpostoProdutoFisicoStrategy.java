public class ImpostoProdutoFisicoStrategy implements IImpostoStrategy {
  private final float aliquotaNacional;
  private final float aliquotaEstadual;

  public ImpostoProdutoFisicoStrategy(float aliquotaNacional, float aliquotaEstadual) {
    this.aliquotaNacional = aliquotaNacional;
    this.aliquotaEstadual = aliquotaEstadual;
  }

  @Override
  public float calcularImposto(IProduto produto, float valorBase) {
    return (valorBase * aliquotaNacional / 100) + (valorBase * aliquotaEstadual / 100);
  }

}
