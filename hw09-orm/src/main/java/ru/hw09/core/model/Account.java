package hw09.core.model;

import hw09.annotation.Id;

public class Account {
  @Id
  private final int no;
  private final String type;
  private int rest;

  public Account(int no, String type, int rest) {
    this.no = no;
    this.type = type;
    this.rest = rest;
  }

  public int getNo() {
    return no;
  }

  public String getType() {
    return type;
  }

  public int getRest() {
    return rest;
  }

  public Account setRest(int rest) {
    this.rest = rest;
    return this;
  }

  @Override
  public String toString() {
    return "Account{" +
            "no=" + no +
            ", type='" + type + '\'' +
            ", rest='" + rest + '\'' +
            '}';
  }
}
