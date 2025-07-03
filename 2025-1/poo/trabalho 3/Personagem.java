public class Personagem extends Entidade implements IControlavel {
  private int velocidade;

  public Personagem(Ponto posicao, int tamanho, int velocidade) {
    super(posicao, tamanho);
    this.velocidade = velocidade;
  }

  public int getVelocidade() {
    return velocidade;
  }

  public void setVelocidade(int velocidade) {
    this.velocidade = velocidade;
  }

  public void mover(Direcao direcao) {
    switch (direcao) {
      case CIMA:
        this.getPosicao().setY(this.getPosicao().getY() - velocidade);
        break;
      case BAIXO:
        this.getPosicao().setY(this.getPosicao().getY() + velocidade);
        break;
      case ESQUERDA:
        this.getPosicao().setX(this.getPosicao().getX() - velocidade);
        break;
      case DIREITA:
        this.getPosicao().setX(this.getPosicao().getX() + velocidade);
        break;
    }
  }
}
