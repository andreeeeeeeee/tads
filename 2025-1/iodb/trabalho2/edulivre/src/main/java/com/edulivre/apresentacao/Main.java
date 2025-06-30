package com.edulivre.apresentacao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import com.edulivre.negocio.Conteudo;
import com.edulivre.negocio.Curso;
import com.edulivre.negocio.Matricula;
import com.edulivre.negocio.Perfil;
import com.edulivre.negocio.Tipo;
import com.edulivre.negocio.Usuario;
import com.edulivre.persistencia.ConteudoDAO;
import com.edulivre.persistencia.CursoDAO;
import com.edulivre.persistencia.MatriculaDAO;
import com.edulivre.persistencia.UsuarioDAO;

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
    System.out.println("3. Matricular usuário em curso");
    System.out.println("4. Buscar conteúdos de um curso");
    System.out.println("5. Listar cursos com avaliações");
    System.out.println("6. Visualizar conteúdo");
  }

  private static void mostrarMenuProfessor() {
    System.out.println("1. Cadastrar aluno");
    System.out.println("2. Cadastrar conteúdo em curso");
    System.out.println("3. Matricular aluno em curso");
    System.out.println("4. Buscar conteúdos de um curso");
    System.out.println("5. Listar cursos com avaliações");
    System.out.println("6. Visualizar conteúdo");
  }

  private static void mostrarMenuAluno() {
    System.out.println("1. Matricular-se em curso");
    System.out.println("2. Buscar conteúdos de um curso");
    System.out.println("3. Listar cursos com avaliações");
    System.out.println("4. Avaliar curso");
    System.out.println("5. Visualizar conteúdo");
  }

  private static void processarOpcao(int opcao) {
    if (opcao == 0) {
      System.out.println("Obrigado por usar a Plataforma EduLivre!");
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
        matricularUsuario();
        break;
      case 4:
        buscarConteudosCurso();
        break;
      case 5:
        listarCursosComAvaliacoes();
        break;
      case 6:
        visualizarConteudo();
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
        matricularUsuario();
        break;
      case 4:
        buscarConteudosCurso();
        break;
      case 5:
        listarCursosComAvaliacoes();
        break;
      case 6:
        visualizarConteudo();
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
      case 5:
        visualizarConteudo();
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
    if (!verificarTipoArquivo(nomeArquivo, tipo)) {
      System.out.println("O tipo do arquivo não corresponde ao tipo selecionado!");
      System.out.println("Tipos aceitos para " + tipo.getDescricao() + ": " + getExtensoesAceitas(tipo));
      return;
    }

    byte[] bytesArquivo;
    try {
      bytesArquivo = Files.readAllBytes(arquivo.toPath());
      System.out.println("Arquivo carregado com sucesso! Tamanho: " + formatarTamanho(bytesArquivo.length));
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

  private static void matricularUsuario() {
    System.out.println("\n=== MATRICULAR USUÁRIO ===");
    System.out.print("Email do usuário: ");
    String email = scanner.nextLine();

    Usuario usuario = UsuarioDAO.buscarPorEmail(email);
    if (usuario == null) {
      System.out.println("Usuário não encontrado!");
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
    System.out.println("\n=== MATRICULAR-SE EM CURSO ===");

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
    System.out.print("Digite o título do curso: ");
    String tituloCurso = scanner.nextLine();

    ConteudoDAO.buscarConteudosPorTituloCurso(tituloCurso);
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
    System.out.print("Digite o título do curso: ");
    String tituloCurso = scanner.nextLine();

    Curso curso = CursoDAO.buscarPorTitulo(tituloCurso);
    if (curso == null) {
      System.out.println("Curso não encontrado!");
      return;
    }

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

  private static boolean verificarTipoArquivo(String nomeArquivo, Tipo tipo) {
    String extensao = obterExtensao(nomeArquivo);

    switch (tipo) {
      case VIDEO:
        return extensao.matches("mp4|avi|mkv|mov|wmv|flv|webm");
      case PDF:
        return extensao.equals("pdf");
      case IMAGEM:
        return extensao.matches("jpg|jpeg|png|gif|bmp|svg|webp");
      case AUDIO:
        return extensao.matches("mp3|wav|ogg|flac|aac|m4a");
      case QUIZ:
        return extensao.matches("txt|json|xml");
      case SLIDE:
        return extensao.matches("ppt|pptx|odp");
      default:
        return false;
    }
  }

  private static String obterExtensao(String nomeArquivo) {
    int ultimoPonto = nomeArquivo.lastIndexOf('.');
    if (ultimoPonto > 0 && ultimoPonto < nomeArquivo.length() - 1) {
      return nomeArquivo.substring(ultimoPonto + 1).toLowerCase();
    }
    return "";
  }

  private static String getExtensoesAceitas(Tipo tipo) {
    switch (tipo) {
      case VIDEO:
        return "mp4, avi, mkv, mov, wmv, flv, webm";
      case PDF:
        return "pdf";
      case IMAGEM:
        return "jpg, jpeg, png, gif, bmp, svg, webp";
      case AUDIO:
        return "mp3, wav, ogg, flac, aac, m4a";
      case QUIZ:
        return "txt, json, xml";
      case SLIDE:
        return "ppt, pptx, odp";
      default:
        return "Nenhuma extensão definida";
    }
  }

  private static String formatarTamanho(long bytes) {
    if (bytes == 0)
      return "0 B";

    String[] unidades = { "B", "KB", "MB", "GB", "TB" };
    int unidade = 0;
    double tamanho = bytes;

    while (tamanho >= 1024 && unidade < unidades.length - 1) {
      tamanho /= 1024;
      unidade++;
    }

    return String.format("%.2f %s", tamanho, unidades[unidade]);
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

  private static void visualizarConteudo() {
    System.out.println("\n=== VISUALIZAR CONTEÚDO ===");
    System.out.print("Digite o título do curso: ");
    String tituloCurso = scanner.nextLine();

    Curso curso = CursoDAO.buscarPorTitulo(tituloCurso);
    if (curso == null) {
      System.out.println("Curso não encontrado!");
      return;
    }

    List<Conteudo> conteudos = ConteudoDAO.buscarPorCurso(curso.getId());
    if (conteudos == null || conteudos.isEmpty()) {
      System.out.println("Nenhum conteúdo encontrado para este curso!");
      return;
    }

    System.out.println("\n=== CONTEÚDOS DISPONÍVEIS ===");
    for (int i = 0; i < conteudos.size(); i++) {
      Conteudo conteudo = conteudos.get(i);
      String tamanho = "N/A";
      if (conteudo.getArquivo() != null) {
        tamanho = formatarTamanho(conteudo.getArquivo().length);
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

  private static void exibirDetalhesConteudo(Conteudo conteudo) {
    System.out.println("\n" + "=".repeat(60));
    System.out.println("DETALHES DO CONTEÚDO");
    System.out.println("=".repeat(60));
    System.out.println("Título: " + conteudo.getTitulo());
    System.out.println("Descrição: " + conteudo.getDescricao());
    System.out.println("Tipo: " + conteudo.getTipo().getDescricao());

    if (conteudo.getArquivo() != null) {
      System.out.println("Tamanho: " + formatarTamanho(conteudo.getArquivo().length));
      System.out.println("\nOpções:");
      System.out.println("1. Baixar arquivo");
      System.out.println("2. Visualizar conteúdo (texto/dados)");
      System.out.println("0. Voltar");

      System.out.print("Escolha uma opção: ");
      int opcao = lerInteiro();

      switch (opcao) {
        case 1:
          baixarArquivo(conteudo);
          break;
        case 2:
          visualizarConteudoArquivo(conteudo);
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

  private static void baixarArquivo(Conteudo conteudo) {
    System.out.print("Digite o caminho onde salvar o arquivo: ");
    String caminhoDestino = scanner.nextLine();

    try {
      String extensao = obterExtensaoPadrao(conteudo.getTipo());
      String nomeArquivo = conteudo.getTitulo().replaceAll("[^a-zA-Z0-9]", "_") + "." + extensao;

      File arquivoDestino = new File(caminhoDestino, nomeArquivo);
      Files.write(arquivoDestino.toPath(), conteudo.getArquivo());

      System.out.println("Arquivo salvo com sucesso em: " + arquivoDestino.getAbsolutePath());
    } catch (IOException e) {
      System.out.println("Erro ao salvar arquivo: " + e.getMessage());
    }
  }

  private static void visualizarConteudoArquivo(Conteudo conteudo) {
    System.out.println("\n=== CONTEÚDO DO ARQUIVO ===");

    byte[] arquivo = conteudo.getArquivo();
    if (arquivo == null || arquivo.length == 0) {
      System.out.println("Arquivo vazio ou não disponível.");
      return;
    }

    if (conteudo.getTipo() == Tipo.QUIZ || arquivo.length < 1024) {
      try {
        String conteudoTexto = new String(arquivo, "UTF-8");
        System.out.println(conteudoTexto);
      } catch (Exception e) {
        System.out.println("Não é possível exibir o conteúdo como texto.");
        exibirInformacoesArquivo(arquivo);
      }
    } else {
      System.out.println("Arquivo muito grande para visualização direta.");
      exibirInformacoesArquivo(arquivo);
    }

    System.out.println("\nPressione Enter para continuar...");
    scanner.nextLine();
  }

  private static void exibirInformacoesArquivo(byte[] arquivo) {
    System.out.println("Informações do arquivo:");
    System.out.println("- Tamanho: " + formatarTamanho(arquivo.length));
    System.out.println("- Primeiros bytes (hex): " + bytesToHex(arquivo, 16));
  }

  private static String bytesToHex(byte[] bytes, int limite) {
    StringBuilder result = new StringBuilder();
    int maxBytes = Math.min(bytes.length, limite);

    for (int i = 0; i < maxBytes; i++) {
      result.append(String.format("%02x ", bytes[i]));
    }

    if (bytes.length > limite) {
      result.append("...");
    }

    return result.toString();
  }

  private static String obterExtensaoPadrao(Tipo tipo) {
    switch (tipo) {
      case VIDEO:
        return "mp4";
      case PDF:
        return "pdf";
      case IMAGEM:
        return "jpg";
      case AUDIO:
        return "mp3";
      case QUIZ:
        return "txt";
      case SLIDE:
        return "pptx";
      default:
        return "bin";
    }
  }
}