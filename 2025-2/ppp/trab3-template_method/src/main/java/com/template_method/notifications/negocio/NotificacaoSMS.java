package com.template_method.notifications.negocio;

public class NotificacaoSMS extends Notificacao {
  @Override
  protected void validarDestinatario(String destinatario) {
    if (!destinatario.matches("\\d{11,}")) {
      throw new IllegalArgumentException("Número de telefone inválido: " + destinatario);
    }
    System.out.println("Telefone validado: " + destinatario);
  }

  @Override
  protected void montarMensagem(String mensagem) {
    System.out.println("Montando mensagem de SMS: " + mensagem);
  }

  @Override
  protected void enviarNotificacao(String destinatario, String mensagem) {
    System.out.println("Enviando SMS para " + destinatario + ": " + mensagem);
  }
}
