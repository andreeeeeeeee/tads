
public class ProdutoDigital extends Produto {
  private String urlDownload;
  private float tamanhoMB;

  public ProdutoDigital(String nome, EnumTipoProduto tipo, float valorBase) {
    super(nome, tipo, valorBase);
  }

  public String getUrlDownload() {
    return this.urlDownload;
  }

  public void setUrlDownload(String urlDownload) {
    this.urlDownload = urlDownload;
  }

  public float getTamanhoMB() {
    return this.tamanhoMB;
  }

  public void setTamanhoMB(float tamanhoMB) {
    this.tamanhoMB = tamanhoMB;
  }
}
