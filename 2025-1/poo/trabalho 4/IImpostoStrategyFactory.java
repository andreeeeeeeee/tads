public interface IImpostoStrategyFactory {
  IImpostoStrategy createImpostoStrategy(EnumTipoProduto tipoProduto, float aliquota);
}
