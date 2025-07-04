import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;

public class Jogo extends JPanel implements KeyListener, Runnable {
  private boolean cima = false;
  private boolean baixo = false;
  private boolean esquerda = false;
  private boolean direita = false;

  private Jogador jogador;
  private Fase faseAtual;
  private int numeroFaseAtual = 1;
  private Random random = new Random();
  private boolean gameOver = false;

  public Jogo() {
    inicializarJogo();
  }

  private void inicializarJogo() {
    jogador = new Jogador(new Ponto(50, 50), 5);
    cima = false;
    baixo = false;
    esquerda = false;
    direita = false;
    criarNovaFase();
  }

  private void criarNovaFase() {
    Ponto posicaoObjetivo;
    do {
      posicaoObjetivo = new Ponto(
          random.nextInt(700) + 50,
          random.nextInt(500) + 50);
    } while (posicaoObjetivo.calcularDistancia(jogador.getPosicao()) < 100
        || !posicaoObjetivo.estaNoLimite());

    Objetivo objetivo = new Objetivo(posicaoObjetivo);

    int quantidadeInimigos = numeroFaseAtual + 1;
    Inimigo[] inimigos = new Inimigo[quantidadeInimigos];

    for (int i = 0; i < quantidadeInimigos; i++) {
      Ponto posicaoInimigo;
      do {
        posicaoInimigo = new Ponto(
            random.nextInt(700) + 50,
            random.nextInt(500) + 50);
      } while (posicaoInimigo.calcularDistancia(jogador.getPosicao()) < 80
          || posicaoInimigo.calcularDistancia(posicaoObjetivo) < 80
          || !posicaoInimigo.estaNoLimite());

      int velocidadeInimigo = (int) (1.4f * numeroFaseAtual) + 3;
      inimigos[i] = new Inimigo(posicaoInimigo, velocidadeInimigo);
    }

    Escudo escudo = null;
    if (numeroFaseAtual <= 3)
      faseAtual = new Fase(numeroFaseAtual, inimigos, objetivo);
    else {
      Ponto posicaoEscudo;
      do {
        posicaoEscudo = new Ponto(
            random.nextInt(700) + 50,
            random.nextInt(500) + 50);
      } while (posicaoEscudo.calcularDistancia(jogador.getPosicao()) < 80
          || posicaoEscudo.calcularDistancia(posicaoObjetivo) < 80
          || !posicaoEscudo.estaNoLimite());

      escudo = new Escudo(posicaoEscudo);
      faseAtual = new Fase(numeroFaseAtual, inimigos, objetivo, escudo);
    }

    jogador.setVelocidade((int) (1.2f * numeroFaseAtual) + 3);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (gameOver) {
      g.setColor(Color.RED);
      g.drawString("GAME OVER - Pressione R para reiniciar", 250, 300);
      return;
    }

    if (jogador.temEscudo()) {
      g.setColor(Color.ORANGE);
      g.fillRect(
          jogador.getPosicao().getX() - 5,
          jogador.getPosicao().getY() - 5,
          jogador.getTamanho() + 10,
          jogador.getTamanho() + 10);
    }

    g.setColor(Color.BLUE);
    g.fillRect(
        jogador.getPosicao().getX(),
        jogador.getPosicao().getY(),
        jogador.getTamanho(),
        jogador.getTamanho());

    g.setColor(Color.GREEN);
    g.fillRect(
        faseAtual.getObjetivo().getPosicao().getX(),
        faseAtual.getObjetivo().getPosicao().getY(),
        faseAtual.getObjetivo().getTamanho(),
        faseAtual.getObjetivo().getTamanho());

    g.setColor(Color.RED);
    for (Inimigo inimigo : faseAtual.getInimigos())
      g.fillRect(
          inimigo.getPosicao().getX(),
          inimigo.getPosicao().getY(),
          inimigo.getTamanho(),
          inimigo.getTamanho());

    if (faseAtual.getEscudo() != null) {
      g.setColor(Color.ORANGE);
      g.fillOval(
          faseAtual.getEscudo().getPosicao().getX(),
          faseAtual.getEscudo().getPosicao().getY(),
          faseAtual.getEscudo().getTamanho(),
          faseAtual.getEscudo().getTamanho());
    }

    g.setColor(Color.BLACK);
    g.drawString("Fase: " + faseAtual.getNumero(), 10, 20);
    g.drawString("Inimigos: " + faseAtual.getInimigos().length, 10, 40);
    if (jogador.temEscudo())
      g.drawString("ESCUDO ATIVO", 10, 60);

    if (cima)
      jogador.mover(Direcao.CIMA);
    if (baixo)
      jogador.mover(Direcao.BAIXO);
    if (esquerda)
      jogador.mover(Direcao.ESQUERDA);
    if (direita)
      jogador.mover(Direcao.DIREITA);

    for (Inimigo inimigo : faseAtual.getInimigos()) {
      if (random.nextInt(20) == 0)
        inimigo.moverAleatoriamente();
    }

    verificarColisoes();
  }

  private void verificarColisoes() {
    if (jogador.colidiu(faseAtual.getObjetivo())) {
      numeroFaseAtual++;
      criarNovaFase();
      return;
    }

    if (jogador.colidiu(faseAtual.getEscudo())) {
      jogador.coletarEscudo();
      faseAtual.removerEscudo();
    }

    for (Inimigo inimigo : faseAtual.getInimigos()) {
      if (jogador.colidiu(inimigo)) {
        if (jogador.temEscudo()) {
          jogador.usarEscudo();
          reiniciarJogo(numeroFaseAtual);
        } else
          gameOver = true;

        return;
      }
    }
  }

  private void reiniciarJogo(int faseInicial) {
    gameOver = false;
    numeroFaseAtual = faseInicial;
    inicializarJogo();
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (gameOver && (e.getKeyChar() == 'r' || e.getKeyChar() == 'R')) {
      reiniciarJogo(1);
      return;
    }

    if (gameOver)
      return;

    if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
      cima = true;
    else if (e.getKeyChar() == 's' || e.getKeyChar() == 'S')
      baixo = true;
    else if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
      esquerda = true;
    else if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
      direita = true;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (gameOver)
      return;

    if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
      cima = false;
    else if (e.getKeyChar() == 's' || e.getKeyChar() == 'S')
      baixo = false;
    else if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
      esquerda = false;
    else if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
      direita = false;
  }

  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(16);
      } catch (Exception e) {
      }
      this.repaint();
    }
  }
}
