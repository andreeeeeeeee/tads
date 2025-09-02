package com.observer.email.apresentacao;

import com.observer.email.EmailService;
import com.observer.email.EmailObserver;

public class Main {
  public static void main(String[] args) {
    EmailService emailService = new EmailService();
    EmailObserver observer1 = new EmailObserver("usuario1@email.com");
    EmailObserver observer2 = new EmailObserver("usuario2@email.com");

    System.out.println();
    emailService.sendEmail("Bom dia!");

    emailService.addObserver(observer1);
    emailService.addObserver(observer2);
    emailService.sendEmail("Bom dia!");

    System.out.println();

    emailService.removeObserver(observer1);
    emailService.sendEmail("Como vai?");
  }
}