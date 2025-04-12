public class Pagamento {
    private String tipo;
    private Cartao cartao;

    public Pagamento(String tipo) {
        this.tipo = tipo;
    }

    public Pagamento(String tipo, Cartao cartao) {
        this.tipo = tipo;
        this.cartao = cartao;
    }

    public boolean pagar(float valor) {
        if (tipo.equals("PIX")) {
            System.out.println("PROCESSANDO PIX DE R$ " + valor);
            return true;
        }

        if (tipo.equals("CARTAO_CREDITO")) {
            System.out.println("PROCESSANDO CARTAO DE CREDITO DE R$ " + valor);
            return this.cartao.processarCompra(valor);
        }

        if (tipo.equals("DINHEIRO")) {
            
        }

        return false;
    }
}
