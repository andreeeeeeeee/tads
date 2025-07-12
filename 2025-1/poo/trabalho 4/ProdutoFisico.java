
public class ProdutoFisico extends Produto {
  private float pesoKG;
  private String dimensoes;
  private int estoque;

  public ProdutoFisico(String nome, EnumTipoProduto tipo, float valorBase) {
    super(nome, tipo, valorBase);
  }

  public float getPesoKG() {
    return this.pesoKG;
  }

  public void setPesoKG(float pesoKG) {
    this.pesoKG = pesoKG;
  }

  public String getDimensoes() {
    return this.dimensoes;
  }

  public void setDimensoes(String dimensoes) {
    this.dimensoes = dimensoes;
  }

  public int getEstoque() {
    return this.estoque;
  }

  public void setEstoque(int estoque) {
    this.estoque = estoque;
  }
}
