public class Produto {
    private String nome;
    private float preco;

    Produto(String nome, float preco) {
        setNome(nome);
        setPreco(preco);
    }

    @Override
    public String toString() {
        return "Nome: "+ this.nome + "\nPreço: R$ " + this.preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco < 0 ? 0 : preco;
    }
}
