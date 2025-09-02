package com.observer.email;

public class EmailService extends Subject {
  public void sendEmail(String message) {
    notifyObservers(message);
  }
}
