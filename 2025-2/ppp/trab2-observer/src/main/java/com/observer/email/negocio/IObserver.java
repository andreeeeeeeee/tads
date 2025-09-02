package com.observer.email;

public interface IObserver<T> {
  void update(T notification);
}
