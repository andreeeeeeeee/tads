public class IsencaoImpostoStrategy implements IImpostoStrategy {

  @Override
  public float calcularImposto(IProduto produto, float valorBase) {
    // No tax for exempt products
    return 0;
  }

}
