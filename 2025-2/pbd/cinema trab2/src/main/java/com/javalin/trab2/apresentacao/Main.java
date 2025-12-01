package com.javalin.trab2.apresentacao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javalin.trab2.negocio.CinemaService;
import com.javalin.trab2.negocio.Cliente;
import com.javalin.trab2.negocio.ConflitoDeSessao;
import com.javalin.trab2.negocio.Filme;
import com.javalin.trab2.negocio.Poltrona;
import com.javalin.trab2.negocio.RelatorioClienteFrequente;
import com.javalin.trab2.negocio.RelatorioOcupacao;
import com.javalin.trab2.negocio.Sessao;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.javalin.rendering.template.JavalinMustache;

public class Main {

  private static final CinemaService cinemaService = new CinemaService();
  private static final ObjectMapper objectMapper = new ObjectMapper()
      .registerModule(new JavaTimeModule());

  public static void main(String[] args) {
    Javalin app = Javalin.create(config -> {
      config.fileRenderer(new JavalinMustache());
      config.jsonMapper(new JavalinJackson(objectMapper, true));
    }).start(7070);

    app.get("/", ctx -> {
      Map<String, Object> model = new HashMap<>();
      model.put("titulo", "Sistema de Cinema - Trabalho 2");
      ctx.render("/templates/index.html", model);
    });

    app.get("/relatorio/clientes-frequentes", ctx -> {
      try {
        List<RelatorioClienteFrequente> relatorio = cinemaService.gerarRelatorioClientesFrequentes();
        ctx.json(relatorio);
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro ao gerar relatório: " + e.getMessage()));
      }
    });

    app.get("/relatorio/clientes-frequentes/view", ctx -> {
      try {
        List<RelatorioClienteFrequente> relatorio = cinemaService.gerarRelatorioClientesFrequentes();
        Map<String, Object> model = new HashMap<>();
        model.put("clientes", relatorio);
        model.put("titulo", "Relatório de Clientes Frequentes");
        ctx.render("/templates/relatorio_clientes.html", model);
      } catch (SQLException e) {
        ctx.status(500).html("<h1>Erro ao gerar relatório: " + e.getMessage() + "</h1>");
      }
    });

    app.get("/sessoes/conflitos", ctx -> {
      try {
        List<ConflitoDeSessao> conflitos = cinemaService.verificarSessoesSimultaneas();
        ctx.json(conflitos);
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro ao verificar conflitos: " + e.getMessage()));
      }
    });

    app.get("/sessoes/conflitos/view", ctx -> {
      try {
        List<ConflitoDeSessao> conflitos = cinemaService.verificarSessoesSimultaneas();
        Map<String, Object> model = new HashMap<>();
        model.put("conflitos", conflitos);
        model.put("titulo", "Conflitos de Sessões Simultâneas");
        ctx.render("/templates/conflitos_sessoes.html", model);
      } catch (SQLException e) {
        ctx.status(500).html("<h1>Erro ao verificar conflitos: " + e.getMessage() + "</h1>");
      }
    });

    app.post("/sessoes/{id}/simular-ocupacao", ctx -> {
      try {
        int sessaoId = Integer.parseInt(ctx.pathParam("id"));
        Map<String, Object> requestBody = objectMapper.readValue(ctx.body(), Map.class);
        double percentual = ((Number) requestBody.get("percentual")).doubleValue();

        int poltronasOcupadas = cinemaService.simularOcupacaoSessao(sessaoId, percentual);

        Map<String, Object> response = new HashMap<>();
        response.put("sessao_id", sessaoId);
        response.put("percentual_simulado", percentual);
        response.put("poltronas_ocupadas", poltronasOcupadas);
        response.put("sucesso", true);

        ctx.json(response);
      } catch (NumberFormatException e) {
        ctx.status(400).json(Map.of("erro", "ID da sessão inválido"));
      } catch (IllegalArgumentException e) {
        ctx.status(400).json(Map.of("erro", e.getMessage()));
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro na simulação: " + e.getMessage()));
      }
    });

    app.get("/sessoes/simular", ctx -> {
      try {
        List<Sessao> sessoes = cinemaService.listarSessoesDisponiveis();
        Map<String, Object> model = new HashMap<>();
        model.put("sessoes", sessoes);
        model.put("titulo", "Simulação de Ocupação");
        ctx.render("/templates/simular_ocupacao.html", model);
      } catch (SQLException e) {
        ctx.status(500).html("<h1>Erro ao carregar sessões: " + e.getMessage() + "</h1>");
      }
    });

    app.post("/sessoes/ajustar-precos", ctx -> {
      try {
        int ingressosAjustados = cinemaService.ajustarPrecosBaixaOcupacao();

        Map<String, Object> response = new HashMap<>();
        response.put("ingressos_ajustados", ingressosAjustados);
        response.put("sucesso", true);
        response.put("mensagem", ingressosAjustados + " ingressos tiveram seus preços ajustados (redução de 20%)");

        ctx.json(response);
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro no ajuste de preços: " + e.getMessage()));
      }
    });

    app.post("/ingressos/remanejar", ctx -> {
      try {
        Map<String, Object> requestBody = objectMapper.readValue(ctx.body(), Map.class);
        String cpf = (String) requestBody.get("cpf");
        int sessaoId = ((Number) requestBody.get("sessao_id")).intValue();
        int poltronaAntiga = ((Number) requestBody.get("poltrona_antiga")).intValue();
        int poltronaNova = ((Number) requestBody.get("poltrona_nova")).intValue();

        boolean sucesso = cinemaService.remanejarPoltrona(cpf, sessaoId, poltronaAntiga, poltronaNova);

        Map<String, Object> response = new HashMap<>();
        response.put("sucesso", sucesso);
        response.put("mensagem", sucesso ? "Remanejamento realizado com sucesso" : "Falha no remanejamento");
        response.put("cpf", cpf);
        response.put("sessao_id", sessaoId);
        response.put("poltrona_antiga", poltronaAntiga);
        response.put("poltrona_nova", poltronaNova);

        ctx.json(response);
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro no remanejamento: " + e.getMessage()));
      } catch (JsonProcessingException e) {
        ctx.status(400).json(Map.of("erro", "Dados inválidos: " + e.getMessage()));
      }
    });

    app.get("/ingressos/remanejar", ctx -> {
      Map<String, Object> model = new HashMap<>();
      model.put("titulo", "Remanejamento de Poltronas");
      ctx.render("/templates/remanejar_poltronas.html", model);
    });

    app.get("/ingressos/vender", ctx -> {
      try {
        List<Sessao> sessoes = cinemaService.listarSessoesDisponiveis();
        Map<String, Object> model = new HashMap<>();
        model.put("sessoes", sessoes);
        model.put("titulo", "Vender Ingresso");
        ctx.render("/templates/vender_ingresso.html", model);
      } catch (SQLException e) {
        ctx.status(500).html("<h1>Erro ao carregar sessões: " + e.getMessage() + "</h1>");
      }
    });

    app.get("/assentos/disponiveis", ctx -> {
      try {
        List<Sessao> sessoes = cinemaService.listarSessoesDisponiveis();
        Map<String, Object> model = new HashMap<>();
        model.put("sessoes", sessoes);
        model.put("titulo", "Assentos Disponíveis");
        ctx.render("/templates/assentos_disponiveis.html", model);
      } catch (SQLException e) {
        ctx.status(500).html("<h1>Erro ao carregar sessões: " + e.getMessage() + "</h1>");
      }
    });

    app.get("/relatorio/ocupacao", ctx -> {
      try {
        List<Sessao> sessoes = cinemaService.listarSessoesDisponiveis();
        Map<String, Object> model = new HashMap<>();
        model.put("sessoes", sessoes);
        model.put("titulo", "Relatório de Ocupação");
        ctx.render("/templates/relatorio_ocupacao.html", model);
      } catch (SQLException e) {
        ctx.status(500).html("<h1>Erro ao carregar sessões: " + e.getMessage() + "</h1>");
      }
    });

    app.get("/filmes", ctx -> {
      try {
        List<Filme> filmes = cinemaService.listarFilmes();
        ctx.json(filmes);
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro ao listar filmes: " + e.getMessage()));
      }
    });

    app.get("/filmes/{id}", ctx -> {
      try {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Filme filme = cinemaService.buscarFilmePorId(id);
        if (filme != null) {
          ctx.json(filme);
        } else {
          ctx.status(404).json(Map.of("erro", "Filme não encontrado"));
        }
      } catch (NumberFormatException e) {
        ctx.status(400).json(Map.of("erro", "ID inválido"));
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro ao buscar filme: " + e.getMessage()));
      }
    });

    app.post("/filmes", ctx -> {
      try {
        Map<String, Object> requestBody = objectMapper.readValue(ctx.body(), Map.class);
        String titulo = (String) requestBody.get("titulo");
        int duracao = ((Number) requestBody.get("duracao")).intValue();
        String classificacao = (String) requestBody.get("classificacao");
        String sinopse = (String) requestBody.get("sinopse");

        boolean sucesso = cinemaService.adicionarFilme(titulo, duracao, classificacao, sinopse);

        if (sucesso) {
          ctx.json(Map.of("sucesso", true, "mensagem", "Filme adicionado com sucesso"));
        } else {
          ctx.status(500).json(Map.of("erro", "Falha ao adicionar filme"));
        }
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro ao adicionar filme: " + e.getMessage()));
      } catch (JsonProcessingException e) {
        ctx.status(400).json(Map.of("erro", "Dados inválidos: " + e.getMessage()));
      }
    });

    app.delete("/filmes/{id}", ctx -> {
      try {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean sucesso = cinemaService.excluirFilme(id);

        if (sucesso) {
          ctx.json(Map.of("sucesso", true, "mensagem", "Filme excluído com sucesso"));
        } else {
          ctx.status(500).json(Map.of("erro", "Falha ao excluir filme"));
        }
      } catch (NumberFormatException e) {
        ctx.status(400).json(Map.of("erro", "ID inválido"));
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro ao excluir filme: " + e.getMessage()));
      }
    });

    app.get("/filmes/diretor/{nome}", ctx -> {
      try {
        String nomeDiretor = ctx.pathParam("nome");
        List<String> titulos = cinemaService.buscarFilmesPorDiretor(nomeDiretor);
        ctx.json(Map.of("diretor", nomeDiretor, "filmes", titulos));
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro ao buscar filmes: " + e.getMessage()));
      }
    });

    app.get("/sessoes/{id}/ocupacao", ctx -> {
      try {
        int sessaoId = Integer.parseInt(ctx.pathParam("id"));
        double percentual = cinemaService.calcularPercentualOcupacao(sessaoId);

        Map<String, Object> response = new HashMap<>();
        response.put("sessao_id", sessaoId);
        response.put("percentual_ocupacao", percentual);

        ctx.json(response);
      } catch (NumberFormatException e) {
        ctx.status(400).json(Map.of("erro", "ID da sessão inválido"));
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro ao calcular ocupação: " + e.getMessage()));
      }
    });

    app.get("/sessoes", ctx -> {
      try {
        List<Sessao> sessoes = cinemaService.listarSessoesDisponiveis();
        ctx.json(sessoes);
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro ao listar sessões: " + e.getMessage()));
      }
    });

    app.post("/clientes", ctx -> {
      try {
        Map<String, Object> requestBody = objectMapper.readValue(ctx.body(), Map.class);

        Cliente cliente = new Cliente();
        cliente.setCpf((String) requestBody.get("cpf"));
        cliente.setNome((String) requestBody.get("nome"));
        cliente.setEmail((String) requestBody.get("email"));
        cliente.setTelefone((String) requestBody.get("telefone"));

        cinemaService.cadastrarCliente(cliente);

        ctx.json(Map.of("sucesso", true, "mensagem", "Cliente cadastrado com sucesso"));
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro ao cadastrar cliente: " + e.getMessage()));
      } catch (JsonProcessingException e) {
        ctx.status(400).json(Map.of("erro", "Dados inválidos: " + e.getMessage()));
      }
    });

    app.get("/sessoes/{id}/ingressos-vendidos", ctx -> {
      try {
        int sessaoId = Integer.parseInt(ctx.pathParam("id"));
        int qtde = cinemaService.contarIngressosVendidos(sessaoId);

        Map<String, Object> response = new HashMap<>();
        response.put("sessao_id", sessaoId);
        response.put("ingressos_vendidos", qtde);

        ctx.json(response);
      } catch (NumberFormatException e) {
        ctx.status(400).json(Map.of("erro", "ID da sessão inválido"));
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro ao contar ingressos: " + e.getMessage()));
      }
    });

    app.get("/sessoes/{id}/assentos-disponiveis", ctx -> {
      try {
        int sessaoId = Integer.parseInt(ctx.pathParam("id"));
        List<Poltrona> poltronas = cinemaService.listarAssentosDisponiveis(sessaoId);

        ctx.json(poltronas);
      } catch (NumberFormatException e) {
        ctx.status(400).json(Map.of("erro", "ID da sessão inválido"));
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro ao listar assentos disponíveis: " + e.getMessage()));
      }
    });

    app.post("/ingressos/vender", ctx -> {
      try {
        Map<String, Object> requestBody = objectMapper.readValue(ctx.body(), Map.class);
        String cpf = (String) requestBody.get("cpf");
        int sessaoId = ((Number) requestBody.get("sessao_id")).intValue();
        int poltronaId = ((Number) requestBody.get("poltrona_id")).intValue();
        double valor = ((Number) requestBody.get("valor")).doubleValue();

        boolean sucesso = cinemaService.venderIngresso(cpf, sessaoId, poltronaId, valor);

        Map<String, Object> response = new HashMap<>();
        response.put("sucesso", sucesso);
        response.put("mensagem", sucesso ? "Ingresso vendido com sucesso" : "Falha na venda");

        ctx.json(response);
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro ao vender ingresso: " + e.getMessage()));
      } catch (JsonProcessingException e) {
        ctx.status(400).json(Map.of("erro", "Dados inválidos: " + e.getMessage()));
      }
    });

    app.get("/sessoes/{id}/relatorio-ocupacao", ctx -> {
      try {
        int sessaoId = Integer.parseInt(ctx.pathParam("id"));
        RelatorioOcupacao relatorio = cinemaService.gerarRelatorioOcupacao(sessaoId);

        if (relatorio != null) {
          ctx.json(relatorio);
        } else {
          ctx.status(404).json(Map.of("erro", "Relatório não encontrado"));
        }
      } catch (NumberFormatException e) {
        ctx.status(400).json(Map.of("erro", "ID da sessão inválido"));
      } catch (SQLException e) {
        ctx.status(500).json(Map.of("erro", "Erro ao gerar relatório: " + e.getMessage()));
      }
    });

    System.out.println("===========================================");
    System.out.println("SISTEMA DE CINEMA - TRABALHO 2");
    System.out.println("===========================================");
    System.out.println("Servidor iniciado em: http://localhost:7070");
    System.out.println();
    System.out.println("ENDPOINTS DISPONÍVEIS:");
    System.out.println("- GET  / - Página inicial");
    System.out.println("- GET  /relatorio/clientes-frequentes - Relatório JSON");
    System.out.println("- GET  /relatorio/clientes-frequentes/view - Relatório HTML");
    System.out.println("- GET  /sessoes/conflitos - Conflitos JSON");
    System.out.println("- GET  /sessoes/conflitos/view - Conflitos HTML");
    System.out.println("- POST /sessoes/{id}/simular-ocupacao - Simular ocupação");
    System.out.println("- GET  /sessoes/simular - Página de simulação");
    System.out.println("- POST /sessoes/ajustar-precos - Ajustar preços");
    System.out.println("- POST /ingressos/remanejar - Remanejar poltronas");
    System.out.println("- GET  /ingressos/remanejar - Página de remanejamento");
    System.out.println("- GET  /filmes - Listar filmes");
    System.out.println("- GET  /filmes/{id} - Buscar filme por ID");
    System.out.println("- POST /filmes - Adicionar novo filme");
    System.out.println("- DELETE /filmes/{id} - Excluir filme");
    System.out.println("- GET  /filmes/diretor/{nome} - Buscar filmes por diretor");
    System.out.println("- GET  /sessoes/{id}/ocupacao - Calcular ocupação");
    System.out.println("- GET  /sessoes - Listar sessões");
    System.out.println("- POST /clientes - Cadastrar cliente");
    System.out.println("- GET  /sessoes/{id}/ingressos-vendidos - Contar ingressos vendidos");
    System.out.println("- GET  /sessoes/{id}/assentos-disponiveis - Listar assentos disponíveis");
    System.out.println("- POST /ingressos/vender - Vender ingresso");
    System.out.println("- GET  /sessoes/{id}/relatorio-ocupacao - Relatório de ocupação");
    System.out.println("- GET  /ingressos/vender - Página para vender ingressos");
    System.out.println("- GET  /assentos/disponiveis - Página para ver assentos disponíveis");
    System.out.println("- GET  /relatorio/ocupacao - Página para relatório de ocupação");
    System.out.println("===========================================");
  }
}