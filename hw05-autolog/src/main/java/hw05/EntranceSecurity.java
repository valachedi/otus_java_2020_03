package hw05;

public class EntranceSecurity implements EntranceSecurityInterface {

  private static final String GREET_PREFIX = "Welcome, ";
  private static final String GREET_POSTFIX = "!";
  private static final String BYE_PREFIX = "It's a private event, ";
  private static final String BYE_POSTFIX = ".";

  public void greetByName(String name) {
    System.out.println(GREET_PREFIX + name + GREET_POSTFIX);
  }


  public void denyByName(String name) {
    System.out.println(BYE_PREFIX + name + BYE_POSTFIX);
  }
}
