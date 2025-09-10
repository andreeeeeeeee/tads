package com.template_method.notifications.apresentacao;

import com.template_method.notifications.negocio.Notificacao;
import com.template_method.notifications.negocio.NotificacaoEmail;
import com.template_method.notifications.negocio.NotificacaoPush;
import com.template_method.notifications.negocio.NotificacaoSMS;

public class Main {
  public static void main(String[] args) {
    Notificacao email = new NotificacaoEmail();
    Notificacao sms = new NotificacaoSMS();
    Notificacao push = new NotificacaoPush();

    System.out.println("\n--- Enviando E-mail ---");
    email.enviar("usuario@email.com", "Bem-vindo ao sistema!");

    System.out.println("\n--- Enviando SMS ---");
    sms.enviar("11999999999", "Seu código é 1234");

    System.out.println("\n--- Enviando Push ---");
    push.enviar("id_dispositivo_abc123", "Você tem uma nova notificação!");
  }
}