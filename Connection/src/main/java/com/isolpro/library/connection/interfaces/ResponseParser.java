package com.isolpro.library.connection.interfaces;

public interface ResponseParser<T> {
  T parse(String data);
}
