public class ImpostoProdutoFisicoStrategy implements IImpostoStrategy {
  private final float aliquotaNacional;
  private final float aliquotaEstadual;

  public ImpostoProdutoFisicoStrategy(float aliquotaNacional, float aliquotaEstadual) {
    this.aliquotaNacional = aliquotaNacional;
    this.aliquotaEstadual = aliquotaEstadual;
  }

  public ImpostoProdutoFisicoStrategy(float aliquotaNacional) {
    this.aliquotaNacional = aliquotaNacional;
    this.aliquotaEstadual = 0;
  }

  public float getAliquotaNacional() {
    return this.aliquotaNacional;
  }

  public float getAliquotaEstadual() {
    return this.aliquotaEstadual;
  }

  @Override
  public float calcularImposto(IProduto produto, float valorBase) {
    if (!(produto instanceof ProdutoFisico))
      throw new IllegalArgumentException("Produto não é do tipo ProdutoFisico");

    float impostoNacional = valorBase * (this.aliquotaNacional / 100);
    float impostoEstadual = valorBase * (this.aliquotaEstadual / 100);

    return impostoNacional + impostoEstadual;
  }
}
