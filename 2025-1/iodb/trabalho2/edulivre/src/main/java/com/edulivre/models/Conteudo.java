package com.edulivre.models;

import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Conteudo {
  private int id;
  private UUID cursoID;
  private String titulo;
  private String descricao;
  private Tipo tipo;
  private byte[] arquivo;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public UUID getCursoID() {
    return cursoID;
  }

  public void setCursoID(UUID cursoID) {
    this.cursoID = cursoID;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Tipo getTipo() {
    return tipo;
  }

  public void setTipo(Tipo tipo) {
    this.tipo = tipo;
  }

  public byte[] getArquivo() {
    return arquivo;
  }

  public void setArquivo(byte[] arquivo) {
    this.arquivo = arquivo;
  }

  public long getTamanhoArquivo() {
    return arquivo != null ? arquivo.length : 0;
  }

  public String getTamanhoArquivoFormatado() {
    if (arquivo == null)
      return "N/A";

    long bytes = arquivo.length;
    if (bytes == 0)
      return "0 B";

    String[] unidades = { "B", "KB", "MB", "GB" };
    int unidade = 0;
    double tamanho = bytes;

    while (tamanho >= 1024 && unidade < unidades.length - 1) {
      tamanho /= 1024;
      unidade++;
    }

    return String.format("%.2f %s", tamanho, unidades[unidade]);
  }

  /**
   * Método principal para exibir conteúdo baseado no tipo
   */
  public void exibirConteudo() {
    if (arquivo == null || arquivo.length == 0) {
      System.out.println("Arquivo não disponível!");
      return;
    }

    switch (this.tipo) {
      case IMAGEM:
        exibirImagem();
        break;
      case PDF:
        exibirPDF();
        break;
      case VIDEO:
        exibirVideo();
        break;
      case AUDIO:
        exibirAudio();
        break;
      case QUIZ:
        exibirQuiz();
        break;
      case SLIDE:
        exibirSlide();
        break;
      default:
        System.out.println("Tipo de arquivo não suportado para visualização!");
    }
  }

  /**
   * Exibe imagens em uma janela Swing
   */
  private void exibirImagem() {
    JFrame frame = new JFrame("Visualizador de Imagem - " + titulo);
    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    frame.setSize(800, 600);

    ImageIcon icon = new ImageIcon(arquivo);
    JLabel label = new JLabel(icon, JLabel.CENTER);

    JScrollPane scrollPane = new JScrollPane(label);
    frame.add(scrollPane);

    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  /**
   * Exibe conteúdo de PDF salvando temporariamente e abrindo no visualizador
   * padrão
   */
  private void exibirPDF() {
    try {
      File tempFile = File.createTempFile("edulivre_pdf_", ".pdf");
      tempFile.deleteOnExit();

      Files.write(tempFile.toPath(), arquivo);

      if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().open(tempFile);
        System.out.println("PDF aberto no visualizador padrão do sistema.");
      } else {
        System.out.println("Não é possível abrir automaticamente. PDF salvo em: " + tempFile.getAbsolutePath());
      }
    } catch (IOException e) {
      System.out.println("Erro ao exibir PDF: " + e.getMessage());
    }
  }

  /**
   * Exibe vídeo salvando temporariamente e abrindo no player padrão
   */
  private void exibirVideo() {
    try {
      String extensao = obterExtensaoPadrao();
      File tempFile = File.createTempFile("edulivre_video_", "." + extensao);
      tempFile.deleteOnExit();

      Files.write(tempFile.toPath(), arquivo);

      if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().open(tempFile);
        System.out.println("Vídeo aberto no player padrão do sistema.");
      } else {
        System.out.println("Não é possível abrir automaticamente. Vídeo salvo em: " + tempFile.getAbsolutePath());
      }
    } catch (IOException e) {
      System.out.println("Erro ao exibir vídeo: " + e.getMessage());
    }
  }

  /**
   * Exibe áudio salvando temporariamente e abrindo no player padrão
   */
  private void exibirAudio() {
    try {
      String extensao = obterExtensaoPadrao();
      File tempFile = File.createTempFile("edulivre_audio_", "." + extensao);
      tempFile.deleteOnExit();

      Files.write(tempFile.toPath(), arquivo);

      if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().open(tempFile);
        System.out.println("Áudio aberto no player padrão do sistema.");
      } else {
        System.out.println("Não é possível abrir automaticamente. Áudio salvo em: " + tempFile.getAbsolutePath());
      }
    } catch (IOException e) {
      System.out.println("Erro ao exibir áudio: " + e.getMessage());
    }
  }

  /**
   * Exibe conteúdo de quiz em uma janela de texto
   */
  private void exibirQuiz() {
    JFrame frame = new JFrame("Quiz - " + titulo);
    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    frame.setSize(600, 400);

    JTextArea textArea = new JTextArea();
    textArea.setText(new String(arquivo));
    textArea.setEditable(false);
    textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);

    JScrollPane scrollPane = new JScrollPane(textArea);
    frame.add(scrollPane);

    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    System.out.println("Quiz exibido em janela de texto.");
  }

  /**
   * Exibe slides salvando temporariamente e abrindo no aplicativo padrão
   */
  private void exibirSlide() {
    try {
      String extensao = obterExtensaoPadrao();
      File tempFile = File.createTempFile("edulivre_slide_", "." + extensao);
      tempFile.deleteOnExit();

      Files.write(tempFile.toPath(), arquivo);

      if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().open(tempFile);
        System.out.println("Apresentação aberta no aplicativo padrão do sistema.");
      } else {
        System.out
            .println("Não é possível abrir automaticamente. Apresentação salva em: " + tempFile.getAbsolutePath());
      }
    } catch (IOException e) {
      System.out.println("Erro ao exibir apresentação: " + e.getMessage());
    }
  }

  public static boolean verificarTipoArquivo(String nomeArquivo, Tipo tipo) {
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

  public static String getExtensoesAceitas(Tipo tipo) {
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

  public static String formatarTamanho(long bytes) {
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

  public String formatarTamanho() {
    long bytes = getTamanhoArquivo();
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

  public String bytesToHex(int limite) {
    byte[] bytes = this.getArquivo();
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

  public String obterExtensaoPadrao() {
    switch (this.tipo) {
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
