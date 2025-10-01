package com.iterator.negocio;

public interface Iterator<T> {
  boolean hasNext();

  T next();
}