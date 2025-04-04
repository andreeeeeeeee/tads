public class Aluno {
    private String nome;
    private float nota;

    Aluno(String nome) {
        this.nome = nome;
    }

    public String toString() {
        return this.nome;
    }

    String getNome(){
        return this.nome;
    }

    float getNota() {
        return this.nota;
    }

    void setNota(float nota) {
        this.nota = nota;
    }
}
