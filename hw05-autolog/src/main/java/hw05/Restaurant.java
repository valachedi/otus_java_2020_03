package hw05;

import java.util.Arrays;

public class Restaurant {
  private static final String[] GUEST_NAMES_WHITELIST = {"Tom", "Bill", "Fox"};

  public void checkIncomingGuestsByNames(String[] incomingGuestNames) {
    EntranceSecurityInterface entranceSecurity = EntranceSecurityFactory.createInstance();

    for(String eachGuestName : incomingGuestNames) {
      if(isNameInWhiteList(eachGuestName)) {
        entranceSecurity.greetByName(eachGuestName);
      } else {
        entranceSecurity.denyByName(eachGuestName);
      }
    }
  }

  private boolean isNameInWhiteList(String name) {
    return Arrays.asList(GUEST_NAMES_WHITELIST).contains(name);
  }
}
