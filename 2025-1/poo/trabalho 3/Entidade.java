public class Entidade {
  private Ponto posicao;
  private int tamanho;

  public Entidade(Ponto posicao, int tamanho) {
    this.posicao = posicao;
    this.tamanho = tamanho;
  }

  public Ponto getPosicao() {
    return posicao;
  }

  public void setPosicao(Ponto posicao) {
    this.posicao = posicao;
  }

  public int getTamanho() {
    return tamanho;
  }

  public void setTamanho(int tamanho) {
    this.tamanho = tamanho;
  }
}
