public class Inimigo extends Personagem {
  private static final int TAMANHO = 18;

  public Inimigo(Ponto posicao, int velocidade) {
    super(posicao, TAMANHO, velocidade);
  }

  public void moverAleatoriamente() {
    Direcao[] direcoes = Direcao.values();
    int indiceAleatorio = (int) (Math.random() * direcoes.length);
    Direcao direcaoAleatoria = direcoes[indiceAleatorio];
    mover(direcaoAleatoria);
  }
}
