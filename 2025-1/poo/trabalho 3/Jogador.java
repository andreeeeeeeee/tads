public class Jogador extends Personagem {
  private boolean temEscudo;

  public Jogador(Ponto posicao, int velocidade) {
    super(posicao, 20, velocidade);
    this.temEscudo = false;
  }

  public boolean colidiu(Entidade entidade) {
    if (entidade == null)
      return false;

    float distanciaMinima = (this.getTamanho() + entidade.getTamanho()) / 2.0f;
    int distanciaX = Math.abs(this.getPosicao().getX() - entidade.getPosicao().getX());
    int distanciaY = Math.abs(this.getPosicao().getY() - entidade.getPosicao().getY());

    return distanciaX < distanciaMinima && distanciaY < distanciaMinima;
  }

  public boolean temEscudo() {
    return temEscudo;
  }

  public void coletarEscudo() {
    temEscudo = true;
  }

  public void usarEscudo() {
    temEscudo = false;
  }
}
