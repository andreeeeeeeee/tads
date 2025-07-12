
public class Assinatura extends Produto {
  private int periodoMeses;
  private EnumTipoRecorrencia recorrencia;

  public Assinatura(String nome, EnumTipoProduto tipo, float valorBase) {
    super(nome, tipo, valorBase);
  }

  public int getPeriodoMeses() {
    return this.periodoMeses;
  }

  public void setPeriodoMeses(int periodoMeses) {
    this.periodoMeses = periodoMeses;
  }

  public EnumTipoRecorrencia getRecorrencia() {
    return this.recorrencia;
  }

  public void setRecorrencia(EnumTipoRecorrencia recorrencia) {
    this.recorrencia = recorrencia;
  }
}
