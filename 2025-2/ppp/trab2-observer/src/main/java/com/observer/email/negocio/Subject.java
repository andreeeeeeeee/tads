package com.observer.email;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject<T> {
  private List<IObserver> observers = new ArrayList<>();

  public void addObserver(IObserver observer) {
    observers.add(observer);
  }

  public void removeObserver(IObserver observer) {
    observers.remove(observer);
  }

  public void notifyObservers(T notificacion) {
    for (IObserver observer : observers)
      observer.update(notificacion);
  }
}
