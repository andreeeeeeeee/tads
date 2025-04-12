import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private List<Produto> produtos;

    Pedido() {
        this.produtos = new ArrayList<>();
    }

    public void addProduto(Produto produto) {
        this.produtos.add(produto);
    }

    public void processarPagamento(Pagamento pagamento) {
        float valorTotal = 0f;
        for (Produto produto : produtos) {
            System.out.println(produto);
            valorTotal += produto.getPreco();
        }

        System.out.println("SITUACAO PAGAMENTO");
        boolean resultadoPagamento = pagamento.pagar(valorTotal);
        System.out.println(resultadoPagamento);
    }
}
