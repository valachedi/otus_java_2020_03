package hw05;

public class Main {
  private static final String[] GUEST_NAMES_INCOME = {"Tom", "Jack", "Bill", "Fox", "Walter"};

  public static void main(String ...args) {
    Restaurant restaurant = new Restaurant();
    restaurant.checkIncomingGuestsByNames(GUEST_NAMES_INCOME);
  }
}
