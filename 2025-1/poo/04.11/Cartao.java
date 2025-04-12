public class Cartao {
    private String numero;
    private float limite = 100;

    public Cartao(String numero) {
        setNumero(numero);
    }

    public Cartao(String numero, float limite) {
        setNumero(numero);
        setLimite(limite);
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public float getLimite() {
        return limite;
    }

    public void setLimite(float limite) {
        this.limite = limite;
    }

    public boolean processarCompra(float valor) {
        if (valor > limite)
            return false;

        this.limite -= valor;
        return true;
    }
}
