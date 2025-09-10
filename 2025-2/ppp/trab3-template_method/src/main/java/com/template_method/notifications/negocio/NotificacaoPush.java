package com.template_method.notifications.negocio;

public class NotificacaoPush extends Notificacao {
  @Override
  protected void validarDestinatario(String destinatario) {
    if (destinatario == null || destinatario.isEmpty()) {
      throw new IllegalArgumentException("ID de push inválido: " + destinatario);
    }
    System.out.println("ID de push validado: " + destinatario);
  }

  @Override
  protected void montarMensagem(String mensagem) {
    System.out.println("Montando mensagem de Push: " + mensagem);
  }

  @Override
  protected void enviarNotificacao(String destinatario, String mensagem) {
    System.out.println("Enviando Push para " + destinatario + ": " + mensagem);
  }
}
