package hw08.components;

import hw08.components.SerializatorInterface;

public abstract class AbstractSerializator implements SerializatorInterface {
  protected String quote(String s) {
    return "\"" + s + "\"";
  }
}
