
import java.util.Date;
import java.util.UUID;

public class Main {
  public static void main(String[] args) {
    Cliente cliente = new Cliente();
    cliente.setNome("João");
    cliente.setEmail("joao@email.com");
    cliente.setDataNascimento(new Date());
    cliente.setEndereco("Rua Exemplo, 123");
    System.out.println("Cliente: " + cliente.getNome() + ", Email: " + cliente.getEmail());

    ProdutoFisico produtoFisico = new ProdutoFisico("Livro de POO", EnumTipoProduto.FISICO, 59.90f);
    produtoFisico.setPesoKG(0.5f);
    produtoFisico.setDimensoes("20x15x2 cm");
    produtoFisico.setEstoque(100);
    System.out.println("Produto Físico: " + produtoFisico.getNome() +
        ", Peso: " + produtoFisico.getPesoKG() + "kg" +
        ", Dimensões: " + produtoFisico.getDimensoes() +
        ", Estoque: " + produtoFisico.getEstoque());

    ProdutoDigital produtoDigital = new ProdutoDigital("Curso de POO", EnumTipoProduto.DIGITAL, 199.90f);
    produtoDigital.setUrlDownload("http://example.com/curso-poo");
    produtoDigital.setTamanhoMB(500.0f);
    System.out.println("Produto Digital: " + produtoDigital.getNome() +
        ", URL de Download: " + produtoDigital.getUrlDownload() +
        ", Tamanho: " + produtoDigital.getTamanhoMB() + "MB");

    Pedido pedido = new Pedido(UUID.randomUUID(), new Date(), cliente);
    ItemPedido itemPedido = new ItemPedido(produtoFisico, 2);
    pedido.adicionarItem(itemPedido);
    pedido.calcularTotais();
    System.out.println("Pedido do Cliente: " + pedido.getCliente().getNome() +
        ", Data do Pedido: " + pedido.getDataPedido() +
        ", Valor Total Bruto: " + pedido.getValorTotalBruto());

    CalculadoraPedido calculadora = new CalculadoraPedido(new DescontoMesAniversarioStrategy(10));
    calculadora.processarPedido(pedido);

    System.out.println("Valor Total Líquido do Pedido: " + pedido.getValorTotalLiquido());
    System.out.println("Valor Total de Descontos: " + pedido.getValorTotalDescontos());
    System.out.println("Valor Total de Impostos: " + pedido.getValorTotalImpostos());
    System.out.println("Itens do Pedido:");
    for (ItemPedido item : pedido.getItens()) {
      System.out.println(" - " + item.getProduto().getNome() +
          ", Quantidade: " + item.getQuantidade() +
          ", Valor Unitário: " + item.getValorUnitario() +
          ", Valor Total: " + item.getValorTotalItem() +
          ", Desconto: " + item.getDescontoAplicado() +
          ", Imposto: " + item.getImpostoAplicado() +
          ", Valor Líquido: " + item.getValorLiquidoItem());
    }
  }
}