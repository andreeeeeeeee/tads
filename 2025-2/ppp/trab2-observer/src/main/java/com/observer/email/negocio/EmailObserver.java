package com.observer.email;

public class EmailObserver implements IObserver<String> {
  private String user;

  public EmailObserver(String user) {
    this.user = user;
  }

  @Override
  public void update(String mensagem) {
    // Simula envio de e-mail
    System.out.println("E-mail enviado para " + user + ": " + mensagem);
  }
}
