
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Main {
  public static void main(String[] args) {
    System.out.println("===============================================================");
    System.out.println("          SISTEMA DE PEDIDOS COM ESTRATÉGIAS DINÂMICAS         ");
    System.out.println("===============================================================");
    System.out.println();

    Cliente cliente1 = criarCliente("Ana Silva", "ana@email.com", 2000, Calendar.JULY, 15, "Rua das Flores, 123");
    Cliente cliente2 = criarCliente("Carlos Mendes", "carlos@email.com", 1995, Calendar.DECEMBER, 10,
        "Av. Principal, 456");
    Cliente cliente3 = criarCliente("Maria Santos", "maria@email.com", 1988, Calendar.MARCH, 25, "Rua Central, 789");

    ProdutoDigital[] produtosDigitais = criarProdutosDigitais();
    ProdutoFisico[] produtosFisicos = criarProdutosFisicos();
    Assinatura[] assinaturas = criarAssinaturas();

    System.out.println("=== DEMONSTRAÇÃO DE PRODUTOS CRIADOS ===");
    demonstrarProdutos(produtosDigitais, produtosFisicos, assinaturas);
    System.out.println();

    System.out.println("=== TESTE 1: DESCONTO MÊS DE ANIVERSÁRIO ===");
    testeDescontoMesAniversario(cliente1, produtosDigitais[0], produtosFisicos[0]);
    System.out.println();

    System.out.println("=== TESTE 2: DESCONTO BLACK FRIDAY ===");
    testeDescontoBlackFriday(cliente2, produtosDigitais[1], assinaturas[0]);
    System.out.println();

    System.out.println("=== TESTE 3: DESCONTO POR VOLUME ===");
    testeDescontoPorVolume(cliente3, produtosFisicos[1]);
    System.out.println();

    System.out.println("=== TESTE 4: PEDIDO SEM DESCONTO ===");
    testePedidoSemDesconto(cliente1, produtosDigitais[2]);
    System.out.println();

    System.out.println("=== TESTE 5: PEDIDO MISTO COM MÚLTIPLOS PRODUTOS ===");
    testePedidoMisto(cliente2, produtosDigitais, produtosFisicos, assinaturas);
    System.out.println();

    System.out.println("=== TESTE 6: COMPARAÇÃO DE ESTRATÉGIAS DE DESCONTO ===");
    compararEstrategiasDesconto(cliente1, produtosFisicos[0]);
    System.out.println();

    System.out.println("=== TESTE 7: DEMONSTRAÇÃO DE TIPOS DE RECORRÊNCIA ===");
    demonstrarTiposRecorrencia(cliente3);
    System.out.println();

    System.out.println("=== TESTE 8: TESTE DE LIMITES E VALIDAÇÕES ===");
    testeLimitesValidacoes(cliente1, produtosFisicos[0]);
    System.out.println();

    System.out.println("===============================================================");
    System.out.println("              DEMONSTRAÇÃO COMPLETA FINALIZADA                ");
    System.out.println("===============================================================");
  }

  private static Cliente criarCliente(String nome, String email, int anoNascimento, int mes, int dia, String endereco) {
    Calendar calNascimento = Calendar.getInstance();
    calNascimento.set(anoNascimento, mes, dia);
    return new Cliente(nome, email, calNascimento.getTime(), endereco);
  }

  private static ProdutoDigital[] criarProdutosDigitais() {
    ProdutoDigital[] produtos = new ProdutoDigital[3];

    produtos[0] = new ProdutoDigital("Curso Completo de Java", EnumTipoProduto.DIGITAL, 299.90f);
    produtos[0].setUrlDownload("https://cursos.exemplo.com/java-completo");
    produtos[0].setTamanhoMB(2048.0f);

    produtos[1] = new ProdutoDigital("E-book: Padrões de Projeto", EnumTipoProduto.DIGITAL, 49.90f);
    produtos[1].setUrlDownload("https://downloads.exemplo.com/padroes-projeto.pdf");
    produtos[1].setTamanhoMB(15.5f);

    produtos[2] = new ProdutoDigital("Curso de Spring Boot", EnumTipoProduto.DIGITAL, 199.90f);
    produtos[2].setUrlDownload("https://cursos.exemplo.com/spring-boot");
    produtos[2].setTamanhoMB(1024.0f);

    return produtos;
  }

  private static ProdutoFisico[] criarProdutosFisicos() {
    ProdutoFisico[] produtos = new ProdutoFisico[3];

    produtos[0] = new ProdutoFisico("Livro: Clean Code", EnumTipoProduto.FISICO, 89.90f);
    produtos[0].setPesoKG(0.8f);
    produtos[0].setDimensoes("23x15x3 cm");
    produtos[0].setEstoque(50);

    produtos[1] = new ProdutoFisico("Notebook Gamer", EnumTipoProduto.FISICO, 2499.90f);
    produtos[1].setPesoKG(2.5f);
    produtos[1].setDimensoes("35x25x2 cm");
    produtos[1].setEstoque(10);

    produtos[2] = new ProdutoFisico("Mouse Wireless", EnumTipoProduto.FISICO, 45.90f);
    produtos[2].setPesoKG(0.1f);
    produtos[2].setDimensoes("12x8x4 cm");
    produtos[2].setEstoque(200);

    return produtos;
  }

  private static Assinatura[] criarAssinaturas() {
    Assinatura[] assinaturas = new Assinatura[3];

    assinaturas[0] = new Assinatura("Plano Premium Mensal", EnumTipoProduto.ASSINATURA, 39.90f);
    assinaturas[0].setPeriodoMeses(1);
    assinaturas[0].setRecorrencia(EnumTipoRecorrencia.MENSAL);

    assinaturas[1] = new Assinatura("Plano Premium Trimestral", EnumTipoProduto.ASSINATURA, 99.90f);
    assinaturas[1].setPeriodoMeses(3);
    assinaturas[1].setRecorrencia(EnumTipoRecorrencia.TRIMESTRAL);

    assinaturas[2] = new Assinatura("Plano Premium Anual", EnumTipoProduto.ASSINATURA, 299.90f);
    assinaturas[2].setPeriodoMeses(12);
    assinaturas[2].setRecorrencia(EnumTipoRecorrencia.ANUAL);

    return assinaturas;
  }

  private static void demonstrarProdutos(ProdutoDigital[] digitais, ProdutoFisico[] fisicos, Assinatura[] assinaturas) {
    System.out.println("PRODUTOS DIGITAIS:");
    for (ProdutoDigital produto : digitais) {
      System.out.println("    " + produto.getNome() + " - R$ " + String.format("%.2f", produto.getValorBase()) +
          " (Tamanho: " + produto.getTamanhoMB() + "MB)");
    }

    System.out.println("\nPRODUTOS FÍSICOS:");
    for (ProdutoFisico produto : fisicos) {
      System.out.println("    " + produto.getNome() + " - R$ " + String.format("%.2f", produto.getValorBase()) +
          " (Peso: " + produto.getPesoKG() + "kg, Estoque: " + produto.getEstoque() + ")");
    }

    System.out.println("\nASSINATURAS:");
    for (Assinatura assinatura : assinaturas) {
      System.out.println("    " + assinatura.getNome() + " - R$ " + String.format("%.2f", assinatura.getValorBase()) +
          " (" + assinatura.getRecorrencia() + " - " + assinatura.getPeriodoMeses() + " meses)");
    }
  }

  private static void testeDescontoMesAniversario(Cliente cliente, ProdutoDigital produtoDigital,
      ProdutoFisico produtoFisico) {
    System.out.println("Cliente: " + cliente.getNome() + " (Aniversário: " + cliente.getDataString() + ")");

    Calendar calPedido = Calendar.getInstance();
    calPedido.set(2025, Calendar.JULY, 17);

    Pedido pedido = new Pedido(UUID.randomUUID(), calPedido.getTime(), cliente);
    pedido.adicionarItem(new ItemPedido(produtoDigital, 1));
    pedido.adicionarItem(new ItemPedido(produtoFisico, 2));

    CalculadoraPedido calculadora = new CalculadoraPedido(new DescontoMesAniversarioStrategy(15));
    calculadora.processarPedido(pedido);

    imprimirRelatorioPedido(pedido, "Desconto de 15% no mês de aniversário");
  }

  private static void testeDescontoBlackFriday(Cliente cliente, ProdutoDigital produtoDigital, Assinatura assinatura) {
    System.out.println("Cliente: " + cliente.getNome());

    Calendar calBlackFriday = Calendar.getInstance();
    calBlackFriday.set(2025, Calendar.NOVEMBER, 25);

    Pedido pedido = new Pedido(UUID.randomUUID(), calBlackFriday.getTime(), cliente);
    pedido.adicionarItem(new ItemPedido(produtoDigital, 1));
    pedido.adicionarItem(new ItemPedido(assinatura, 1));

    CalculadoraPedido calculadora = new CalculadoraPedido(new DescontoBlackFridayStrategy(30));
    calculadora.processarPedido(pedido);

    imprimirRelatorioPedido(pedido, "Desconto de 30% na Black Friday");
  }

  private static void testeDescontoPorVolume(Cliente cliente, ProdutoFisico produto) {
    System.out.println("Cliente: " + cliente.getNome());

    Pedido pedido = new Pedido(UUID.randomUUID(), new Date(), cliente);
    pedido.adicionarItem(new ItemPedido(produto, 5));

    CalculadoraPedido calculadora = new CalculadoraPedido(new DescontoPorVolumeStrategy(20, 3));
    calculadora.processarPedido(pedido);

    imprimirRelatorioPedido(pedido, "Desconto de 20% para compras acima de 3 unidades");
  }

  private static void testePedidoSemDesconto(Cliente cliente, ProdutoDigital produto) {
    System.out.println("Cliente: " + cliente.getNome());

    Pedido pedido = new Pedido(UUID.randomUUID(), new Date(), cliente);
    pedido.adicionarItem(new ItemPedido(produto, 1));

    CalculadoraPedido calculadora = new CalculadoraPedido(null);
    calculadora.processarPedido(pedido);

    imprimirRelatorioPedido(pedido, "Sem desconto aplicado");
  }

  private static void testePedidoMisto(Cliente cliente, ProdutoDigital[] digitais, ProdutoFisico[] fisicos,
      Assinatura[] assinaturas) {
    System.out.println("Cliente: " + cliente.getNome());

    Pedido pedido = new Pedido(UUID.randomUUID(), new Date(), cliente);
    pedido.adicionarItem(new ItemPedido(digitais[0], 2));
    pedido.adicionarItem(new ItemPedido(fisicos[0], 1));
    pedido.adicionarItem(new ItemPedido(fisicos[2], 3));
    pedido.adicionarItem(new ItemPedido(assinaturas[2], 1));

    CalculadoraPedido calculadora = new CalculadoraPedido(new DescontoPorVolumeStrategy(10, 2));
    calculadora.processarPedido(pedido);

    imprimirRelatorioPedido(pedido, "Pedido misto com desconto de 10% por volume");
    imprimirDetalhesItens(pedido);
  }

  private static void compararEstrategiasDesconto(Cliente cliente, ProdutoFisico produto) {
    System.out.println("COMPARAÇÃO DE ESTRATÉGIAS para " + cliente.getNome() + ":");

    String[] estrategias = { "Sem desconto", "Desconto aniversário (15%)", "Black Friday (25%)", "Volume (10% p/ 2+)" };
    CalculadoraPedido[] calculadoras = {
        new CalculadoraPedido(null),
        new CalculadoraPedido(new DescontoMesAniversarioStrategy(15)),
        new CalculadoraPedido(new DescontoBlackFridayStrategy(25)),
        new CalculadoraPedido(new DescontoPorVolumeStrategy(10, 2))
    };

    for (int i = 0; i < estrategias.length; i++) {
      Pedido pedido = new Pedido(UUID.randomUUID(), new Date(), cliente);
      pedido.adicionarItem(new ItemPedido(produto, 3));

      calculadoras[i].processarPedido(pedido);

      System.out.println("   " + estrategias[i] + ":");
      System.out.println("     Valor Final: R$ " + String.format("%.2f", pedido.getValorTotalLiquido()) +
          " (Desconto: R$ " + String.format("%.2f", pedido.getValorTotalDescontos()) + ")");
    }
  }

  private static void demonstrarTiposRecorrencia(Cliente cliente) {
    System.out.println("TIPOS DE RECORRÊNCIA para " + cliente.getNome() + ":");

    EnumTipoRecorrencia[] tipos = { EnumTipoRecorrencia.MENSAL, EnumTipoRecorrencia.TRIMESTRAL,
        EnumTipoRecorrencia.ANUAL };
    float[] precos = { 39.90f, 99.90f, 299.90f };
    int[] periodos = { 1, 3, 12 };

    for (int i = 0; i < tipos.length; i++) {
      Assinatura assinatura = new Assinatura("Plano " + tipos[i], EnumTipoProduto.ASSINATURA, precos[i]);
      assinatura.setPeriodoMeses(periodos[i]);
      assinatura.setRecorrencia(tipos[i]);

      System.out.println("    " + assinatura.getNome() + ": R$ " + String.format("%.2f", precos[i]) +
          " (Período: " + periodos[i] + " meses)");
    }
  }

  private static void testeLimitesValidacoes(Cliente cliente, ProdutoFisico produto) {
    System.out.println("TESTES DE LIMITES E VALIDAÇÕES:");

    System.out.println("    Teste com quantidade 0:");
    Pedido pedido1 = new Pedido(UUID.randomUUID(), new Date(), cliente);
    pedido1.adicionarItem(new ItemPedido(produto, 0));

    CalculadoraPedido calculadora1 = new CalculadoraPedido(null);
    calculadora1.processarPedido(pedido1);
    System.out.println("     Valor Total: R$ " + String.format("%.2f", pedido1.getValorTotalLiquido()));

    System.out.println("    Teste com quantidade alta (100):");
    Pedido pedido2 = new Pedido(UUID.randomUUID(), new Date(), cliente);
    pedido2.adicionarItem(new ItemPedido(produto, 100));

    CalculadoraPedido calculadora2 = new CalculadoraPedido(new DescontoPorVolumeStrategy(50, 50));
    calculadora2.processarPedido(pedido2);
    System.out.println("     Valor Total: R$ " + String.format("%.2f", pedido2.getValorTotalLiquido()));
    System.out.println("     Desconto Total: R$ " + String.format("%.2f", pedido2.getValorTotalDescontos()));
  }

  private static void imprimirRelatorioPedido(Pedido pedido, String descricaoEstrategia) {
    System.out.println("RELATÓRIO DO PEDIDO:");
    System.out.println("   Data: " + pedido.getDataString());
    System.out.println("   Estratégia: " + descricaoEstrategia);
    System.out.println("   Valor Bruto: R$ " + String.format("%.2f", pedido.getValorTotalBruto()));
    System.out.println("   Descontos: R$ " + String.format("%.2f", pedido.getValorTotalDescontos()));
    System.out.println("   Impostos: R$ " + String.format("%.2f", pedido.getValorTotalImpostos()));
    System.out.println("   Valor Final: R$ " + String.format("%.2f", pedido.getValorTotalLiquido()));
    System.out.println();
  }

  private static void imprimirDetalhesItens(Pedido pedido) {
    System.out.println("DETALHES DOS ITENS:");
    for (ItemPedido item : pedido.getItens()) {
      System.out.println("    " + item.getProduto().getNome() + " (" + item.getProduto().getTipo() + ")");
      System.out.println("     Quantidade: " + item.getQuantidade());
      System.out.println("     Valor Unit.: R$ " + String.format("%.2f", item.getValorUnitario()));
      System.out.println("     Valor Total: R$ " + String.format("%.2f", item.getValorTotalItem()));
      System.out.println("     Desconto: R$ " + String.format("%.2f", item.getDescontoAplicado()));
      System.out.println("     Imposto: R$ " + String.format("%.2f", item.getImpostoAplicado()));
      System.out.println("     Valor Líquido: R$ " + String.format("%.2f", item.getValorLiquidoItem()));
      System.out.println();
    }
  }
}