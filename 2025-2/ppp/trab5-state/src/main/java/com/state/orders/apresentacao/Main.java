
package com.state.orders.apresentacao;

import com.state.orders.negocio.Pedido;

public class Main {

  public static void main(String[] args) {
    System.out.println("SISTEMA DE CONTROLE DE PEDIDOS E-COMMERCE");
    System.out.println("Demonstração do Padrão State");
    System.out.println("=".repeat(60));

    System.out.println("\nDEMONSTRAÇÃO 1: Fluxo Completo do Pedido");

    Pedido pedido = new Pedido(1, "João Silva", 299.90);
    pedido.exibirPedido();

    pedido.processarPagamento();
    pedido.exibirPedido();

    pedido.enviar();
    pedido.exibirPedido();

    pedido.entregar();
    pedido.exibirPedido();
    System.out.println("\n\n" + "=".repeat(60));

    System.out.println("\nDEMONSTRAÇÃO 2: Cancelamento Após Pagamento");

    Pedido pedido2 = new Pedido(2, "Maria Santos", 149.50);
    pedido2.exibirPedido();

    pedido2.processarPagamento();
    pedido2.exibirPedido();

    pedido2.cancelar();
    pedido2.exibirPedido();
    System.out.println("\n\n" + "=".repeat(60));

    System.out.println("\nDEMONSTRAÇÃO 3: Tentativas de Ações Inválidas");

    Pedido pedido3 = new Pedido(3, "Carlos Oliveira", 89.90);

    System.out.println("\nEstado NOVO - Tentando ações inválidas:");
    pedido3.enviar();
    pedido3.entregar();

    pedido3.processarPagamento();

    System.out.println("\nEstado PAGO - Tentando ações inválidas:");
    pedido3.processarPagamento();
    pedido3.entregar();

    pedido3.enviar();

    System.out.println("\nEstado ENVIADO - Tentando ações inválidas:");
    pedido3.processarPagamento();
    pedido3.enviar();
    pedido3.cancelar();

    pedido3.entregar();

    System.out.println("\nEstado ENTREGUE - Tentando ações inválidas:");
    pedido3.processarPagamento();
    pedido3.enviar();
    pedido3.entregar();
    pedido3.cancelar();

    pedido3.exibirPedido();
  }
}
