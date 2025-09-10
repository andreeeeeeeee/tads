package com.template_method.notifications.negocio;

public abstract class Notificacao {
  public final void enviar(String destinatario, String mensagem) {
    validarDestinatario(destinatario);
    montarMensagem(mensagem);
    enviarNotificacao(destinatario, mensagem);
    registrarEnvio(destinatario);
  }

  protected abstract void validarDestinatario(String destinatario);

  protected abstract void montarMensagem(String mensagem);

  protected abstract void enviarNotificacao(String destinatario, String mensagem);

  protected void registrarEnvio(String destinatario) {
    System.out.println("Notificação registrada para: " + destinatario);
  }
}
