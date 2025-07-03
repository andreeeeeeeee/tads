public class Fase {
  private int numero;
  private Inimigo[] inimigos;
  private Objetivo objetivo;
  private Escudo escudo;

  public Fase(int numero, Inimigo[] inimigos, Objetivo objetivo) {
    this.numero = numero;
    this.inimigos = inimigos;
    this.objetivo = objetivo;
    this.escudo = null;
  }

  public Fase(int numero, Inimigo[] inimigos, Objetivo objetivo, Escudo escudo) {
    this.numero = numero;
    this.inimigos = inimigos;
    this.objetivo = objetivo;
    this.escudo = escudo;
  }

  public int getNumero() {
    return numero;
  }

  public Inimigo[] getInimigos() {
    return inimigos;
  }

  public Objetivo getObjetivo() {
    return objetivo;
  }

  public Escudo getEscudo() {
    return escudo;
  }

  public void removerEscudo() {
    escudo = null;
  }
}
