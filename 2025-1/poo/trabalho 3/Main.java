import javax.swing.JFrame;

public class Main {
  public static void main(String[] args) {
    JFrame frame = new JFrame("Jogo");
    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Jogo jogo = new Jogo();

    frame.add(jogo);
    frame.addKeyListener(jogo);

    frame.setVisible(true);

    jogo.run();
  }
}
