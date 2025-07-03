public class Ponto {
  private int x;
  private int y;

  private static final int MIN_X = 0;
  private static final int MAX_X = 765;
  private static final int MIN_Y = 0;
  private static final int MAX_Y = 542;

  public Ponto(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return "x: " + x + ", y: " + y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    if (x <= MIN_X)
      this.x = MIN_X;
    else if (x >= MAX_X)
      this.x = MAX_X;
    else
      this.x = x;
  }

  public void setY(int y) {
    if (y <= MIN_Y)
      this.y = MIN_Y;
    else if (y >= MAX_Y)
      this.y = MAX_Y;
    else
      this.y = y;
  }

  public boolean estaNoLimite() {
    return x >= MIN_X
        && x <= MAX_X
        && y >= MIN_Y
        && y <= MAX_Y;
  }
}
