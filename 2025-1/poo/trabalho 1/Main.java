public class Main {
  public static void main(String[] args) {
    Biblioteca biblioteca = new Biblioteca();
    Livro livro1 = new Livro("O Senhor dos Anéis", "J. R. R. Tolkien");
    Livro livro2 = new Livro("As Crônicas de Nárnia", "C. S. Lewis");
    Livro livro3 = new Livro("A Metamorfose", "Franz Kafka");

    biblioteca.adicionarLivro(livro1);
    biblioteca.adicionarLivro(livro2);
    biblioteca.adicionarLivro(livro3);

    biblioteca.emprestarLivro("O Senhor dos Anéis");

    biblioteca.emprestarLivro("O Senhor dos Anéis");

    biblioteca.devolverLivro("O Senhor dos Anéis");

    biblioteca.devolverLivro("A Metamorfose");
    biblioteca.listarLivros();
  }
}
