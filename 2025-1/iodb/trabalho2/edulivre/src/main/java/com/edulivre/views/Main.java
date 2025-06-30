package com.edulivre.views;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import com.edulivre.DAOs.ConteudoDAO;
import com.edulivre.DAOs.CursoDAO;
import com.edulivre.DAOs.MatriculaDAO;
import com.edulivre.DAOs.UsuarioDAO;
import com.edulivre.models.Conteudo;
import com.edulivre.models.Curso;
import com.edulivre.models.Matricula;
import com.edulivre.models.Perfil;
import com.edulivre.models.Tipo;
import com.edulivre.models.Usuario;

public class Main {
  // Curso cursoJavaScript = new Curso();
  // cursoJavaScript.setTitulo("Curso de JavaScript");
  // cursoJavaScript.setDescricao("JavaScript básico ao avançado");
  // cursoJavaScript.setAvaliacao(new JSONObject("{\"media\": 0, \"comentarios\":
  // []}"));

  // Usuario usuarioAndre = new Usuario();
  // usuarioAndre.setNome("André");
  // usuarioAndre.setEmail("andre@gmail.com");
  // usuarioAndre.setPerfil(Perfil.ADMIN);
  // usuarioAndre.setSenha("12345");

  // CursoDAO.inserir(cursoJavaScript);
  // UsuarioDAO.inserir(usuarioAndre);

  // usuarioAndre.addAvaliacao(cursoJavaScript, 5, "òtimo",
  // Date.valueOf("2025-06-30"));

  // Usuario usuarioJoao = UsuarioDAO.buscarPorEmail("joao@email.com");
  // Usuario usuarioMaria = UsuarioDAO.buscarPorEmail("maria@email.com");
  // Curso cursoPSQL = CursoDAO.buscarPorTitulo("Curso de PostgreSQL");

  // usuarioJoao.addAvaliacao(cursoPSQL, 4, "bom", Date.valueOf("2025-06-30"));
  // usuarioMaria.addAvaliacao(cursoPSQL, 4.5f, "bom",
  // Date.valueOf("2025-06-30"));

  // Matricula matriculaJoao = new Matricula();
  // matriculaJoao.setUsuarioID(usuarioJoao.getId());
  // matriculaJoao.setCursoID(cursoPSQL.getId());

  // List<Curso> cursos = CursoDAO.listar();

  // for (Curso curso : cursos) {
  // System.out.println("\nCurso: " + curso.getTitulo() + "\n" + "Média: " +
  // curso.getAvaliacao().getDouble("media") +
  // "\n" + "Avaliações: " +
  // curso.getAvaliacao().getJSONArray("comentarios").length() + "\n" +
  // "Descrição: " + curso.getDescricao() + "\n" + "Data de criação: " +
  // curso.getDataCriacao() + "\n"
  // + "Matriculados: " +
  // MatriculaDAO.contarMatriculasPorCurso(curso.getId()));
  // }

  // System.out.println("\n" + "=".repeat(80));
  // System.out.println("DEMONSTRAÇÃO: Buscar conteúdos de um curso com tipo e
  // tamanho");
  // System.out.println("=".repeat(80));

  // System.out.println("Buscando conteúdos do Curso de Java:");
  // ConteudoDAO.buscarConteudosPorTituloCurso("Curso de Java");

  // System.out.println("\n" + "-".repeat(50));

  // System.out.println("Buscando conteúdos do Curso de PostgreSQL:");
  // ConteudoDAO.buscarConteudosPorTituloCurso("Curso de PostgreSQL");
  private static Scanner scanner = new Scanner(System.in);
  private static Usuario usuarioLogado;

  public static void main(String[] args) {
    System.out.println("=".repeat(50));
    System.out.println("    BEM-VINDO À PLATAFORMA EDULIVRE");
    System.out.println("=".repeat(50));

    if (realizarLogin()) {
      mostrarMenuPrincipal();
    } else {
      System.err.println("Login falhou. Encerrando aplicação.");
    }

    scanner.close();
  }

  private static boolean realizarLogin() {
    System.out.println("\n=== LOGIN ===");
    System.out.print("Email: ");
    String email = scanner.nextLine();
    System.out.print("Senha: ");
    String senha = scanner.nextLine();

    Usuario usuario = UsuarioDAO.buscarPorEmail(email);
    if (usuario != null && usuario.getSenha().equals(senha)) {
      usuarioLogado = usuario;
      System.out.println("\nLogin realizado com sucesso!");
      System.out.println("Bem-vindo(a), " + usuario.getNome() + " (" + usuario.getPerfil().getDescricao() + ")");
      return true;
    } else {
      System.out.println("Email ou senha incorretos!");
      return false;
    }
  }

  private static void mostrarMenuPrincipal() {
    int opcao;
    do {
      System.out.println("\n" + "=".repeat(50));
      System.out.println("MENU PRINCIPAL - " + usuarioLogado.getPerfil().getDescricao().toUpperCase());
      System.out.println("=".repeat(50));

      switch (usuarioLogado.getPerfil()) {
        case ADMIN:
          mostrarMenuAdmin();
          break;
        case PROFESSOR:
          mostrarMenuProfessor();
          break;
        case ALUNO:
          mostrarMenuAluno();
          break;
      }

      System.out.println("0. Sair");
      System.out.print("Escolha uma opção: ");
      opcao = lerInteiro();

      processarOpcao(opcao);
    } while (opcao != 0);
  }

  private static void mostrarMenuAdmin() {
    System.out.println("1. Cadastrar usuário");
    System.out.println("2. Cadastrar curso");
    System.out.println("3. Matricular usuário em um curso");
    System.out.println("4. Buscar conteúdos de um curso");
    System.out.println("5. Listar cursos com avaliações");
  }

  private static void mostrarMenuProfessor() {
    System.out.println("1. Cadastrar aluno");
    System.out.println("2. Cadastrar conteúdo em um curso");
    System.out.println("3. Matricular aluno em um curso");
    System.out.println("4. Buscar conteúdos de um curso");
    System.out.println("5. Listar cursos com avaliações");
  }

  private static void mostrarMenuAluno() {
    System.out.println("1. Matricular-se em um curso");
    System.out.println("2. Buscar conteúdos de um curso");
    System.out.println("3. Listar cursos com avaliações");
    System.out.println("4. Avaliar curso");
  }

  private static void processarOpcao(int opcao) {
    if (opcao == 0) {
      System.out.println("Obrigado por usar a Plataforma EduLivre!");
      System.exit(0);
      return;
    }

    switch (usuarioLogado.getPerfil()) {
      case ADMIN:
        processarOpcaoAdmin(opcao);
        break;
      case PROFESSOR:
        processarOpcaoProfessor(opcao);
        break;
      case ALUNO:
        processarOpcaoAluno(opcao);
        break;
    }
  }

  private static void processarOpcaoAdmin(int opcao) {
    switch (opcao) {
      case 1:
        cadastrarUsuario();
        break;
      case 2:
        cadastrarCurso();
        break;
      case 3:
        matricularAluno();
        break;
      case 4:
        buscarConteudosCurso();
        break;
      case 5:
        listarCursosComAvaliacoes();
        break;
      default:
        System.out.println("Opção inválida!");
    }
  }

  private static void processarOpcaoProfessor(int opcao) {
    switch (opcao) {
      case 1:
        cadastrarAluno();
        break;
      case 2:
        cadastrarConteudo();
        break;
      case 3:
        matricularAluno();
        break;
      case 4:
        buscarConteudosCurso();
        break;
      case 5:
        listarCursosComAvaliacoes();
        break;
      default:
        System.out.println("Opção inválida!");
    }
  }

  private static void processarOpcaoAluno(int opcao) {
    switch (opcao) {
      case 1:
        matricularSeEmCurso();
        break;
      case 2:
        buscarConteudosCurso();
        break;
      case 3:
        listarCursosComAvaliacoes();
        break;
      case 4:
        avaliarCurso();
        break;
      default:
        System.out.println("Opção inválida!");
    }
  }

  private static void cadastrarUsuario() {
    System.out.println("\n=== CADASTRAR USUÁRIO ===");
    System.out.print("Nome: ");
    String nome = scanner.nextLine();
    System.out.print("Email: ");
    String email = scanner.nextLine();
    System.out.print("Senha: ");
    String senha = scanner.nextLine();
    System.out.println("Perfil:");
    System.out.println("1. Aluno");
    System.out.println("2. Professor");
    System.out.println("3. Admin");
    System.out.print("Escolha o perfil: ");
    int perfilOpcao = lerInteiro();

    Perfil perfil;
    switch (perfilOpcao) {
      case 1:
        perfil = Perfil.ALUNO;
        break;
      case 2:
        perfil = Perfil.PROFESSOR;
        break;
      case 3:
        perfil = Perfil.ADMIN;
        break;
      default:
        System.out.println("Perfil inválido!");
        return;
    }

    Usuario usuario = new Usuario();
    usuario.setNome(nome);
    usuario.setEmail(email);
    usuario.setSenha(senha);
    usuario.setPerfil(perfil);

    if (UsuarioDAO.inserir(usuario)) {
      System.out.println("Usuário cadastrado com sucesso!");
    } else {
      System.out.println("Erro ao cadastrar usuário!");
    }
  }

  private static void cadastrarAluno() {
    System.out.println("\n=== CADASTRAR ALUNO ===");
    System.out.print("Nome: ");
    String nome = scanner.nextLine();
    System.out.print("Email: ");
    String email = scanner.nextLine();
    System.out.print("Senha: ");
    String senha = scanner.nextLine();

    Usuario usuario = new Usuario();
    usuario.setNome(nome);
    usuario.setEmail(email);
    usuario.setSenha(senha);
    usuario.setPerfil(Perfil.ALUNO);

    if (UsuarioDAO.inserir(usuario)) {
      System.out.println("Aluno cadastrado com sucesso!");
    } else {
      System.out.println("Erro ao cadastrar aluno!");
    }
  }

  private static void cadastrarCurso() {
    System.out.println("\n=== CADASTRAR CURSO ===");
    System.out.print("Título: ");
    String titulo = scanner.nextLine();
    System.out.print("Descrição: ");
    String descricao = scanner.nextLine();

    Curso curso = new Curso();
    curso.setTitulo(titulo);
    curso.setDescricao(descricao);
    curso.setAvaliacao(new JSONObject("{\"media\": 0, \"comentarios\": []}"));

    if (CursoDAO.inserir(curso)) {
      System.out.println("Curso cadastrado com sucesso!");
    } else {
      System.out.println("Erro ao cadastrar curso!");
    }
  }

  private static void cadastrarConteudo() {
    System.out.println("\n=== CADASTRAR CONTEÚDO ===");

    List<Curso> cursos = CursoDAO.listar();
    if (cursos == null || cursos.isEmpty()) {
      System.out.println("Nenhum curso disponível!");
      return;
    }

    System.out.println("Cursos disponíveis:");
    for (int i = 0; i < cursos.size(); i++) {
      System.out.println((i + 1) + ". " + cursos.get(i).getTitulo());
    }

    System.out.print("Escolha o curso: ");
    int cursoIndex = lerInteiro() - 1;
    if (cursoIndex < 0 || cursoIndex >= cursos.size()) {
      System.out.println("Curso inválido!");
      return;
    }

    Curso cursoSelecionado = cursos.get(cursoIndex);

    System.out.print("Título do conteúdo: ");
    String titulo = scanner.nextLine();
    System.out.print("Descrição: ");
    String descricao = scanner.nextLine();

    System.out.println("Tipo de conteúdo:");
    System.out.println("1. Vídeo");
    System.out.println("2. PDF");
    System.out.println("3. Imagem");
    System.out.println("4. Áudio");
    System.out.println("5. Quiz");
    System.out.println("6. Slide");
    System.out.print("Escolha o tipo: ");
    int tipoOpcao = lerInteiro();

    Tipo tipo;
    switch (tipoOpcao) {
      case 1:
        tipo = Tipo.VIDEO;
        break;
      case 2:
        tipo = Tipo.PDF;
        break;
      case 3:
        tipo = Tipo.IMAGEM;
        break;
      case 4:
        tipo = Tipo.AUDIO;
        break;
      case 5:
        tipo = Tipo.QUIZ;
        break;
      case 6:
        tipo = Tipo.SLIDE;
        break;
      default:
        System.out.println("Tipo inválido!");
        return;
    }

    System.out.print("Caminho do arquivo: ");
    String caminhoArquivo = scanner.nextLine();

    File arquivo = new File(caminhoArquivo);
    if (!arquivo.exists()) {
      System.out.println("Arquivo não encontrado!");
      return;
    }

    if (!arquivo.isFile()) {
      System.out.println("O caminho especificado não é um arquivo!");
      return;
    }

    String nomeArquivo = arquivo.getName().toLowerCase();
    if (!Conteudo.verificarTipoArquivo(nomeArquivo, tipo)) {
      System.out.println("O tipo do arquivo não corresponde ao tipo selecionado!");
      System.out.println("Tipos aceitos para " + tipo.getDescricao() + ": " + Conteudo.getExtensoesAceitas(tipo));
      return;
    }

    byte[] bytesArquivo;
    try {
      bytesArquivo = Files.readAllBytes(arquivo.toPath());
      System.out.println("Arquivo carregado com sucesso! Tamanho: " + Conteudo.formatarTamanho(bytesArquivo.length));
    } catch (IOException e) {
      System.out.println("Erro ao ler o arquivo: " + e.getMessage());
      return;
    }

    Conteudo conteudo = new Conteudo();
    conteudo.setCursoID(cursoSelecionado.getId());
    conteudo.setTitulo(titulo);
    conteudo.setDescricao(descricao);
    conteudo.setTipo(tipo);
    conteudo.setArquivo(bytesArquivo);

    if (ConteudoDAO.inserir(conteudo)) {
      System.out.println("Conteúdo cadastrado com sucesso!");
    } else {
      System.out.println("Erro ao cadastrar conteúdo!");
    }
  }

  private static void matricularAluno() {
    System.out.println("\n=== MATRICULAR ALUNO ===");
    System.out.print("Email do aluno: ");
    String email = scanner.nextLine();

    Usuario usuario = UsuarioDAO.buscarPorEmail(email);
    if (usuario == null) {
      System.out.println("Aluno não encontrado!");
      return;
    }

    if (usuario.getPerfil() != Perfil.ALUNO) {
      System.out.println("Somente alunos podem ser matriculados!");
      return;
    }

    List<Curso> cursos = CursoDAO.listar();
    if (cursos == null || cursos.isEmpty()) {
      System.out.println("Nenhum curso disponível!");
      return;
    }

    System.out.println("Cursos disponíveis:");
    for (int i = 0; i < cursos.size(); i++) {
      System.out.println((i + 1) + ". " + cursos.get(i).getTitulo());
    }

    System.out.print("Escolha o curso: ");
    int cursoIndex = lerInteiro() - 1;
    if (cursoIndex < 0 || cursoIndex >= cursos.size()) {
      System.out.println("Curso inválido!");
      return;
    }

    Curso cursoSelecionado = cursos.get(cursoIndex);

    Matricula matricula = new Matricula();
    matricula.setUsuarioID(usuario.getId());
    matricula.setCursoID(cursoSelecionado.getId());
    matricula.setDataMatricula(Date.valueOf(LocalDate.now()));

    if (MatriculaDAO.inserir(matricula)) {
      System.out.println("Usuário matriculado com sucesso!");
    } else {
      System.out.println("Erro ao matricular usuário!");
    }
  }

  private static void matricularSeEmCurso() {
    System.out.println("\n=== MATRICULAR-SE EM UM CURSO ===");

    List<Curso> cursos = CursoDAO.listar();
    if (cursos == null || cursos.isEmpty()) {
      System.out.println("Nenhum curso disponível!");
      return;
    }

    System.out.println("Cursos disponíveis:");
    for (int i = 0; i < cursos.size(); i++) {
      System.out.println((i + 1) + ". " + cursos.get(i).getTitulo());
    }

    System.out.print("Escolha o curso: ");
    int cursoIndex = lerInteiro() - 1;
    if (cursoIndex < 0 || cursoIndex >= cursos.size()) {
      System.out.println("Curso inválido!");
      return;
    }

    Curso cursoSelecionado = cursos.get(cursoIndex);

    Matricula matricula = new Matricula();
    matricula.setUsuarioID(usuarioLogado.getId());
    matricula.setCursoID(cursoSelecionado.getId());
    matricula.setDataMatricula(Date.valueOf(LocalDate.now()));

    if (MatriculaDAO.inserir(matricula)) {
      System.out.println("Você foi matriculado com sucesso!");
    } else {
      System.out.println("Erro ao realizar matrícula!");
    }
  }

  private static void buscarConteudosCurso() {
    System.out.println("\n=== BUSCAR CONTEÚDOS DE CURSO ===");

    List<Curso> cursos = usuarioLogado.obterCursosDisponiveis();
    if (cursos == null || cursos.isEmpty()) {
      System.out.println("Nenhum curso disponível!");
      return;
    }

    System.out.println("Cursos disponíveis:");
    for (int i = 0; i < cursos.size(); i++) {
      System.out.println((i + 1) + ". " + cursos.get(i).getTitulo());
    }

    System.out.print("Escolha o curso: ");
    int cursoIndex = lerInteiro() - 1;
    if (cursoIndex < 0 || cursoIndex >= cursos.size()) {
      System.out.println("Curso inválido!");
      return;
    }

    Curso cursoSelecionado = cursos.get(cursoIndex);
    List<Conteudo> conteudos = ConteudoDAO.buscarPorCurso(cursoSelecionado.getId());
    if (conteudos == null || conteudos.isEmpty()) {
      System.out.println("Nenhum conteúdo encontrado para este curso!");
      return;
    }
    System.out.println("\n=== CONTEÚDOS DISPONÍVEIS ===");
    for (int i = 0; i < conteudos.size(); i++) {
      Conteudo conteudo = conteudos.get(i);
      String tamanho = "N/A";
      if (conteudo.getArquivo() != null) {
        tamanho = conteudo.formatarTamanho();
      }

      System.out.printf("%d. %-30s %-10s %-10s%n",
          (i + 1),
          conteudo.getTitulo(),
          conteudo.getTipo().getDescricao(),
          tamanho);
    }

    System.out.print("\nEscolha o conteúdo para visualizar (0 para voltar): ");
    int opcao = lerInteiro();

    if (opcao == 0) {
      return;
    }

    if (opcao < 1 || opcao > conteudos.size()) {
      System.out.println("Opção inválida!");
      return;
    }

    Conteudo conteudoSelecionado = conteudos.get(opcao - 1);
    exibirDetalhesConteudo(conteudoSelecionado);
  }

  private static void listarCursosComAvaliacoes() {
    System.out.println("\n=== CURSOS COM AVALIAÇÕES ===");
    List<Curso> cursos = CursoDAO.listar();

    if (cursos == null || cursos.isEmpty()) {
      System.out.println("Nenhum curso disponível!");
      return;
    }

    System.out.printf("%-30s %-10s %-15s %-12s%n", "Título", "Média", "Avaliações", "Matriculados");
    System.out.println("=".repeat(70));

    for (Curso curso : cursos) {
      double media = 0;
      int totalAvaliacoes = 0;

      if (curso.getAvaliacao() != null) {
        if (curso.getAvaliacao().has("media")) {
          media = curso.getAvaliacao().getDouble("media");
        }
        if (curso.getAvaliacao().has("comentarios")) {
          totalAvaliacoes = curso.getAvaliacao().getJSONArray("comentarios").length();
        }
      }

      int matriculados = MatriculaDAO.contarMatriculasPorCurso(curso.getId());

      System.out.printf("%-30s %-10.1f %-15d %-12d%n",
          curso.getTitulo(), media, totalAvaliacoes, matriculados);
    }
  }

  private static void avaliarCurso() {
    System.out.println("\n=== AVALIAR CURSO ===");

    List<Curso> cursos = usuarioLogado.obterCursosDisponiveis();
    if (cursos == null || cursos.isEmpty()) {
      System.out.println("Nenhum curso disponível!");
      return;
    }

    System.out.println("Cursos disponíveis:");
    for (int i = 0; i < cursos.size(); i++) {
      System.out.println((i + 1) + ". " + cursos.get(i).getTitulo());
    }

    System.out.print("Escolha o curso para avaliar: ");
    int cursoIndex = lerInteiro() - 1;
    if (cursoIndex < 0 || cursoIndex >= cursos.size()) {
      System.out.println("Curso inválido!");
      return;
    }

    Curso curso = cursos.get(cursoIndex);

    System.out.print("Nota (1-5): ");
    float nota = lerFloat();
    if (nota < 1 || nota > 5) {
      System.out.println("Nota deve ser entre 1 e 5!");
      return;
    }

    System.out.print("Comentário: ");
    String comentario = scanner.nextLine();

    usuarioLogado.addAvaliacao(curso, nota, comentario, Date.valueOf(LocalDate.now()));
    System.out.println("Avaliação adicionada com sucesso!");
  }

  private static int lerInteiro() {
    try {
      int valor = Integer.parseInt(scanner.nextLine());
      return valor;
    } catch (NumberFormatException e) {
      System.out.println("Valor inválido! Digite um número inteiro.");
      return -1;
    }
  }

  private static float lerFloat() {
    try {
      float valor = Float.parseFloat(scanner.nextLine());
      return valor;
    } catch (NumberFormatException e) {
      System.out.println("Valor inválido! Digite um número.");
      return -1;
    }
  }

  private static void exibirDetalhesConteudo(Conteudo conteudo) {
    System.out.println("\n" + "=".repeat(60));
    System.out.println("DETALHES DO CONTEÚDO");
    System.out.println("=".repeat(60));
    System.out.println("Título: " + conteudo.getTitulo());
    System.out.println("Descrição: " + conteudo.getDescricao());
    System.out.println("Tipo: " + conteudo.getTipo().getDescricao());

    if (conteudo.getArquivo() != null) {
      System.out.println("Tamanho: " + conteudo.formatarTamanho());
      System.out.println("\nOpções:");
      System.out.println("1. Visualizar conteúdo");
      System.out.println("2. Baixar arquivo");
      System.out.println("0. Voltar");

      System.out.print("Escolha uma opção: ");
      int opcao = lerInteiro();

      switch (opcao) {
        case 1:
          visualizarConteudoArquivo(conteudo);
          break;
        case 2:
          baixarArquivo(conteudo);
          break;
        case 0:
          return;
        default:
          System.out.println("Opção inválida!");
      }
    } else {
      System.out.println("Arquivo não disponível!");
    }
  }

  private static void visualizarConteudoArquivo(Conteudo conteudo) {
    System.out.println("\n=== VISUALIZANDO CONTEÚDO ===");
    System.out.println("Tipo: " + conteudo.getTipo().getDescricao());
    System.out.println("Tamanho: " + conteudo.formatarTamanho());

    // Usar o novo método de exibição baseado no tipo
    conteudo.exibirConteudo();

    System.out.println("\nPressione Enter para continuar...");
    scanner.nextLine();
  }

  private static void baixarArquivo(Conteudo conteudo) {
    System.out.println("\n=== BAIXAR ARQUIVO ===");
    System.out.print("Digite o caminho onde deseja salvar o arquivo: ");
    String caminhoDestino = scanner.nextLine();

    // Verificar se o caminho existe
    File diretorio = new File(caminhoDestino);
    if (!diretorio.exists()) {
      System.out.println("Diretório não existe! Deseja criá-lo? (s/n): ");
      String resposta = scanner.nextLine();
      if (resposta.toLowerCase().startsWith("s")) {
        if (!diretorio.mkdirs()) {
          System.out.println("Erro ao criar diretório!");
          return;
        }
      } else {
        System.out.println("Download cancelado!");
        return;
      }
    }

    if (!diretorio.isDirectory()) {
      System.out.println("O caminho especificado não é um diretório!");
      return;
    }

    // Sanitizar nome do arquivo
    String nomeArquivo = sanitizarNomeArquivo(conteudo.getTitulo()) + "." + conteudo.obterExtensaoPadrao();
    String caminhoCompleto = caminhoDestino + File.separator + nomeArquivo;

    try {
      File arquivoDestino = new File(caminhoCompleto);

      // Verificar se arquivo já existe
      if (arquivoDestino.exists()) {
        System.out.println("Arquivo já existe! Deseja sobrescrever? (s/n): ");
        String resposta = scanner.nextLine();
        if (!resposta.toLowerCase().startsWith("s")) {
          System.out.println("Download cancelado!");
          return;
        }
      }

      Files.write(arquivoDestino.toPath(), conteudo.getArquivo());
      System.out.println("Arquivo baixado com sucesso!");
      System.out.println("Local: " + arquivoDestino.getAbsolutePath());
      System.out.println("Tamanho: " + conteudo.formatarTamanho());

    } catch (IOException e) {
      System.out.println("Erro ao salvar arquivo: " + e.getMessage());
    }

    System.out.println("\nPressione Enter para continuar...");
    scanner.nextLine();
  }

  private static String sanitizarNomeArquivo(String nome) {
    // Remove caracteres inválidos para nomes de arquivo
    String nomeSeguro = nome.replaceAll("[\\\\/:*?\"<>|]", "_");
    // Remove espaços extras e substitui por underscores
    nomeSeguro = nomeSeguro.replaceAll("\\s+", "_");
    // Limita o tamanho do nome
    if (nomeSeguro.length() > 50) {
      nomeSeguro = nomeSeguro.substring(0, 50);
    }
    return nomeSeguro;
  }
}