public class Professor {
    private String nome;

    Professor(String nome) {
        this.nome = nome;
    }

    public String toString() {
        return nome;
    }

    String getNome() {
        return this.nome;
    }
}
