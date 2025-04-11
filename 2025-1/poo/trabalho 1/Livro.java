public class Livro {
  private final String titulo;
  private final String autor;
  private boolean disponivel;

  public Livro(String titulo, String autor) {
    this.titulo = titulo;
    this.autor = autor;
    this.disponivel = true;
  }

  @Override
  public String toString() {
    return "Livro: " + this.titulo + " - " + this.autor + " - " + (this.disponivel ? "Disponível" : "Indisponível");
  }

  public String getTitulo() {
    return this.titulo;
  }

  public String getAutor() {
    return this.autor;
  }

  public boolean isDisponivel() {
    return this.disponivel;
  }

  public void setDisponivel(boolean disponivel) {
    this.disponivel = disponivel;
  }
}
