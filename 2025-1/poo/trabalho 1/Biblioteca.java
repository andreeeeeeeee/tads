import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
  private final List<Livro> livros;

  public Biblioteca() {
    this.livros = new ArrayList<>();
  }

  public void adicionarLivro(Livro livro) {
    this.livros.add(livro);
    System.out.println("Livro adicionado\n" + livro);
  }

  public void emprestarLivro(String titulo) {
    for (Livro livro : this.livros) {
      if (livro.getTitulo().equals(titulo)) {
        if (livro.isDisponivel()) {
          livro.setDisponivel(false);
          System.out.println("Livro emprestado\n" + livro);
          return;
        }
        System.out.println("Livro indisponível");
      }
    }
  }

  public void devolverLivro(String titulo) {
    for (Livro livro : this.livros) {
      if (livro.getTitulo().equals(titulo)) {
        if (livro.isDisponivel()) {
          System.out.println("Livro não está emprestado");
          return;
        }
        livro.setDisponivel(true);
        System.out.println("Livro devolvido\n" + livro);
      }
    }
  }

  public void listarLivros() {
    for (Livro livro : this.livros) {
      System.out.println(livro);
    }
  }
}
