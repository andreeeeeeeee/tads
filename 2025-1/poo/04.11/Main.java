public class Main {
    public static void main(String[] args) {
        Produto coca = new Produto("Coca-cola",15);
        Produto pizza = new Produto("Pizza calabresa",69.9f);
        Produto calzone = new Produto("Calzone", 10f);

        Pedido pedido = new Pedido();
        pedido.addProduto(coca);
        pedido.addProduto(pizza);

        Pagamento pagamento = new Pagamento("PIX");
        pedido.processarPagamento(pagamento);

        System.out.println("\n==== PEDIDO 2 ====\n");

        Pedido pedido2 = new Pedido();
        pedido2.addProduto(calzone);

        Pagamento pagamento2 = new Pagamento("CARTAO_CREDITO", new Cartao("123"));
        pedido2.processarPagamento(pagamento2);

        System.out.println("\n==== PEDIDO 3 ====\n");

        Pedido pedido3 = new Pedido();
        pedido3.addProduto(pizza);
        pedido3.addProduto(pizza);

        Pagamento pagamento3 = new Pagamento("CARTAO_CREDITO", new Cartao("123"));
        pedido3.processarPagamento(pagamento3);
    }
}