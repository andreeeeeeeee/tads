package com.template_method.notifications.negocio;

public class NotificacaoEmail extends Notificacao {
  @Override
  protected void validarDestinatario(String destinatario) {
    if (!destinatario.contains("@")) {
      throw new IllegalArgumentException("E-mail inválido: " + destinatario);
    }
    System.out.println("E-mail validado: " + destinatario);
  }

  @Override
  protected void montarMensagem(String mensagem) {
    System.out.println("Montando mensagem de e-mail: " + mensagem);
  }

  @Override
  protected void enviarNotificacao(String destinatario, String mensagem) {
    System.out.println("Enviando e-mail para " + destinatario + ": " + mensagem);
  }
}
