public class ImpostoStrategyFactory implements IImpostoStrategyFactory {
  @Override
  public IImpostoStrategy createImpostoStrategy(EnumTipoProduto tipoProduto, float aliquota) {
    switch (tipoProduto) {
      case DIGITAL -> {
        return new ImpostoProdutoDigitalStrategy(aliquota);
      }
      case FISICO -> {
        return new ImpostoProdutoFisicoStrategy(aliquota);
      }
      case ASSINATURA -> {
        return new ImpostoAssinaturaStrategy(aliquota);
      }
      default -> throw new IllegalArgumentException("Tipo de produto desconhecido: " + tipoProduto);
    }
  }
}
