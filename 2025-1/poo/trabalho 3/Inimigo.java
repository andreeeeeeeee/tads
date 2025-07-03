public class Inimigo extends Personagem {
  public Inimigo(Ponto posicao, int velocidade) {
    super(posicao, 18, velocidade);
  }

  public void moverAleatoriamente() {
    Direcao[] direcoes = Direcao.values();
    int indiceAleatorio = (int) (Math.random() * direcoes.length);
    Direcao direcaoAleatoria = direcoes[indiceAleatorio];
    mover(direcaoAleatoria);
    mover(direcaoAleatoria);
  }
}
