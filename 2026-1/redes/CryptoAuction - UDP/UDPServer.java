import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class UDPServer {

  private static final int PORTA = 10000;
  private static final int MIN_JOGADORES = 2;
  private static final int TEMPO_INICIAL_RODADA = 15;
  private static final int TEMPO_RESET_LANCE = 10;
  private static final String NOME_JOGO = "CryptoAuction";
  private static final Set<Integer> AVISOS_TEMPO = Set.of(10, 5, 3, 2, 1);

  private static final Map<String, Double> ESTOQUE = Map.of(
      "Placa de Vídeo RTX 4090", 5000.0,
      "Processador Intel Core i9-14900K", 2500.0,
      "Monitor Samsung Odyssey G9", 4500.0,
      "Smartphone Samsung Galaxy S24 Ultra", 3800.0,
      "Notebook Dell XPS 15", 7200.0,
      "Console PlayStation 5 Pro", 4200.0,
      "SSD Samsung 990 Pro 2TB", 800.0,
      "Cadeira Gamer DXRacer", 1500.0,
      "Fone de Ouvido Sony WH-1000XM5", 1200.0,
      "Smart TV LG OLED 55\"", 5500.0);
  private static final List<String> ITENS_ESTOQUE = List.copyOf(ESTOQUE.keySet());

  private final Object lock = new Object();
  private final Map<String, Cliente> clientes = new LinkedHashMap<>();
  private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
  private final DecimalFormat formatoMoeda = new DecimalFormat("#,##0",
      DecimalFormatSymbols.getInstance(Locale.forLanguageTag("pt-BR")));

  private DatagramSocket serverSocket;
  private ScheduledFuture<?> cronometro;

  private Estado estado = Estado.AGUARDANDO_JOGADORES;
  private int indiceItem = 0;
  private double lanceAtual;
  private Cliente lanceadorAtual;
  private int tempoRestante;
  private boolean rodadaAtiva;
  private final List<ResultadoRodada> resultados = new ArrayList<>();

  private enum Estado {
    AGUARDANDO_JOGADORES,
    LEILAO_EM_ANDAMENTO,
    PARTIDA_ENCERRADA
  }

  private static class ResultadoRodada {
    final String item;
    final String vencedor;
    final double valor;

    ResultadoRodada(String item, String vencedor, double valor) {
      this.item = item;
      this.vencedor = vencedor;
      this.valor = valor;
    }
  }

  private static class Cliente {
    final InetAddress endereco;
    final int porta;
    final String nickname;

    Cliente(InetAddress endereco, int porta, String nickname) {
      this.endereco = endereco;
      this.porta = porta;
      this.nickname = nickname;
    }

    String chave() {
      return endereco.getHostAddress() + ":" + porta;
    }
  }

  private static class Rota {
    final String nome;
    final String parametro;

    Rota(String nome, String parametro) {
      this.nome = nome;
      this.parametro = parametro;
    }

    static Rota parse(String mensagem) {
      if (!mensagem.startsWith("/"))
        return null;

      int fimRota = mensagem.indexOf('/', 1);
      if (fimRota == -1)
        return new Rota(mensagem.substring(1).toLowerCase(), "");

      String nome = mensagem.substring(1, fimRota).toLowerCase();
      String parametro = mensagem.substring(fimRota + 1).trim();
      return new Rota(nome, parametro);
    }
  }

  public static void main(String[] args) throws Exception {
    new UDPServer().iniciar();
  }

  private void iniciar() throws Exception {
    try (DatagramSocket socket = new DatagramSocket(PORTA)) {
      this.serverSocket = socket;
      System.out.println("Servidor CryptoAuction UDP ativo na porta " + PORTA);

      byte[] buffer = new byte[1024];
      while (true) {
        DatagramPacket pacote = new DatagramPacket(buffer, buffer.length);
        System.out.println("Aguardando datagrama UDP...");
        socket.receive(pacote);

        String mensagem = new String(pacote.getData(), 0, pacote.getLength()).trim();
        InetAddress endereco = pacote.getAddress();
        int porta = pacote.getPort();

        System.out.println("Recebido de " + endereco.getHostAddress() + ":" + porta + " -> " + mensagem);
        processarMensagem(mensagem, endereco, porta);
      }
    } finally {
      scheduler.shutdownNow();
    }
  }

  private void processarMensagem(String mensagem, InetAddress endereco, int porta) {
    Rota rota = Rota.parse(mensagem);
    if (rota == null) {
      enviar(endereco, porta, "Erro: mensagem inválida. Use rotas como /registrar/apelido ou /lance/5200.");
      return;
    }

    synchronized (lock) {
      switch (rota.nome) {
        case "registrar" -> {
          if (rota.parametro.isEmpty())
            enviar(endereco, porta, "Erro: informe o apelido em /registrar/seuApelido.");
          else
            registrarCliente(rota.parametro, endereco, porta);
        }
        case "lance" -> {
          if (rota.parametro.isEmpty()) {
            enviar(endereco, porta, "Erro: informe o valor em /lance/5200.");
            break;
          }
          Cliente cliente = buscarCliente(endereco, porta);
          if (cliente == null)
            enviar(endereco, porta, "Erro: registre-se em /registrar/seuApelido antes de participar.");
          else
            processarLance(cliente, rota.parametro);
        }
        case "nova-partida" -> {
          if (estado == Estado.PARTIDA_ENCERRADA)
            reiniciarPartida();
          else
            enviar(endereco, porta, "Erro: partida ainda em andamento.");
        }
        default -> enviar(endereco, porta,
            "Rota invalida: /" + rota.nome + ". Use /registrar/apelido, /lance/valor ou /nova-partida.");
      }
    }
  }

  private void registrarCliente(String nickname, InetAddress endereco, int porta) {
    if (nickname.isEmpty()) {
      enviar(endereco, porta, "Erro: apelido não pode ser vazio.");
      return;
    }

    if (estado == Estado.LEILAO_EM_ANDAMENTO) {
      enviar(endereco, porta, "Erro: leilão em andamento. Aguarde o fim da partida.");
      return;
    }

    for (Cliente existente : clientes.values())
      if (existente.nickname.equalsIgnoreCase(nickname)
          && !existente.chave().equals(endereco.getHostAddress() + ":" + porta)) {
        enviar(endereco, porta, "Erro: apelido já está em uso.");
        return;
      }

    Cliente cliente = new Cliente(endereco, porta, nickname);
    clientes.put(cliente.chave(), cliente);

    enviar(endereco, porta, "Cliente aceito para o jogo " + NOME_JOGO);
    System.out.println("Cliente registrado: " + nickname + " (" + cliente.chave() + ")");

    if (estado == Estado.AGUARDANDO_JOGADORES && clientes.size() >= MIN_JOGADORES)
      iniciarLeilao();
    else if (estado == Estado.AGUARDANDO_JOGADORES)
      enviar(endereco, porta, "Aguardando mais jogadores (" + clientes.size() + "/" + MIN_JOGADORES + ").");
  }

  private void iniciarLeilao() {
    estado = Estado.LEILAO_EM_ANDAMENTO;
    indiceItem = 0;
    resultados.clear();
    broadcast("Leilão iniciado com " + clientes.size() + " participantes. Itens disponíveis: " + ESTOQUE.size());
    iniciarRodada();
  }

  private void iniciarRodada() {
    if (indiceItem >= ESTOQUE.size()) {
      encerrarPartida();
      return;
    }

    rodadaAtiva = true;
    String item = ITENS_ESTOQUE.get(indiceItem);
    lanceAtual = ESTOQUE.get(item);
    lanceadorAtual = null;
    tempoRestante = TEMPO_INICIAL_RODADA;
    
    broadcast("Item " + (indiceItem + 1) + ": " + item + " - Lance Inicial: R$ " + formatarMoeda(lanceAtual)+ " - Tempo: "
    + tempoRestante + "s");

    iniciarCronometro();
  }

  private void iniciarCronometro() {
    if (cronometro != null)
      cronometro.cancel(false);

    cronometro = scheduler.scheduleAtFixedRate(() -> {
      synchronized (lock) {
        if (!rodadaAtiva || estado != Estado.LEILAO_EM_ANDAMENTO)
          return;

        tempoRestante--;
        
        if (AVISOS_TEMPO.contains(tempoRestante)) {
          broadcast("Tempo restante: " + tempoRestante + "s");
        }

        if (tempoRestante <= 0) {
          finalizarRodada();
        }
      }
    }, 1, 1, TimeUnit.SECONDS);
  }

  private void processarLance(Cliente cliente, String valorTexto) {
    if (estado != Estado.LEILAO_EM_ANDAMENTO || !rodadaAtiva) {
      enviar(cliente.endereco, cliente.porta, "Erro: nenhuma rodada ativa no momento.");
      return;
    }

    double valor;
    try {
      valor = Double.parseDouble(valorTexto.replace(".", "").replace(",", "."));
    } catch (NumberFormatException e) {
      enviar(cliente.endereco, cliente.porta, "Erro: lance invalido. Use /lance/5200");
      return;
    }

    if (valor <= lanceAtual) {
      enviar(cliente.endereco, cliente.porta,
          "Erro: lance deve ser maior que R$ " + formatarMoeda(lanceAtual) + ".");
      return;
    }

    lanceAtual = valor;
    lanceadorAtual = cliente;
    tempoRestante = TEMPO_RESET_LANCE;

    broadcast("Novo Lance por " + cliente.nickname + ": R$ " + formatarMoeda(lanceAtual)
        + ". Tempo restante: " + tempoRestante + "s");
  }

  private void finalizarRodada() {
    rodadaAtiva = false;
    if (cronometro != null) {
      cronometro.cancel(false);
      cronometro = null;
    }

    String item = ITENS_ESTOQUE.get(indiceItem);

    if (lanceadorAtual != null)
      resultados.add(new ResultadoRodada(item, lanceadorAtual.nickname, lanceAtual));
    else
      resultados.add(new ResultadoRodada(item, null, 0));

    if (lanceadorAtual != null)
      broadcast("Rodada encerrada! Vencedor: " + lanceadorAtual.nickname + " arrematou \""
          + item + "\" por R$ " + formatarMoeda(lanceAtual) + ".");
    else
      broadcast("Rodada encerrada! Nenhum lance recebido para \"" + item + "\".");

    indiceItem++;
    if (indiceItem < ESTOQUE.size()) {
      broadcast("Proximo item em 3 segundos...");
      scheduler.schedule(() -> {
        synchronized (lock) {
          if (estado == Estado.LEILAO_EM_ANDAMENTO)
            iniciarRodada();
        }
      }, 3, TimeUnit.SECONDS);
    } else
      encerrarPartida();
  }

  private void encerrarPartida() {
    estado = Estado.PARTIDA_ENCERRADA;
    rodadaAtiva = false;
    if (cronometro != null) {
      cronometro.cancel(false);
      cronometro = null;
    }

    broadcast("Leilao encerrado! Todos os itens foram disputados.");
    broadcast("Resumo dos vencedores:");
    for (int i = 0; i < resultados.size(); i++) {
      ResultadoRodada resultado = resultados.get(i);
      if (resultado.vencedor != null)
        broadcast((i + 1) + ". " + resultado.item + " - " + resultado.vencedor
            + " - R$ " + formatarMoeda(resultado.valor));
      else
        broadcast((i + 1) + ". " + resultado.item + " - Sem lances");
    }
    broadcast("Envie /nova-partida para reiniciar ou /registrar/apelido para entrar em nova sessao.");
    System.out.println("Partida encerrada.");
  }

  private void reiniciarPartida() {
    estado = Estado.AGUARDANDO_JOGADORES;
    indiceItem = 0;
    lanceadorAtual = null;

    if (clientes.size() >= MIN_JOGADORES) {
      broadcast("Nova partida iniciada!");
      iniciarLeilao();
    } else
      broadcast("Nova partida preparada. Aguardando mais jogadores (" + clientes.size() + "/" + MIN_JOGADORES + ").");
    System.out.println("Partida reiniciada.");
  }

  private Cliente buscarCliente(InetAddress endereco, int porta) {
    return clientes.get(endereco.getHostAddress() + ":" + porta);
  }

  private String formatarMoeda(double valor) {
    return formatoMoeda.format(valor);
  }

  private void enviar(InetAddress endereco, int porta, String mensagem) {
    try {
      byte[] dados = mensagem.getBytes();
      DatagramPacket pacote = new DatagramPacket(dados, dados.length, endereco, porta);
      serverSocket.send(pacote);
      System.out.println("Enviado para " + endereco.getHostAddress() + ":" + porta + " -> " + mensagem);
    } catch (IOException e) {
      System.err.println("Falha ao enviar mensagem: " + e.getMessage());
    }
  }

  private void broadcast(String mensagem) {
    List<Cliente> participantes;
    synchronized (lock) {
      participantes = new ArrayList<>(clientes.values());
    }
    for (Cliente cliente : participantes)
      enviar(cliente.endereco, cliente.porta, mensagem);
  }
}
