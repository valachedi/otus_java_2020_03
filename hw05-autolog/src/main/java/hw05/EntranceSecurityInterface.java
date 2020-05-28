package hw05;

public interface EntranceSecurityInterface {
  void greetByName(String name);

  @Log
  void denyByName(String name);
}
