package hw09.core.service;

import java.util.Optional;
import hw09.core.model.Account;

public interface DBServiceAccount {
  int save(Account account);
  Optional<Account> getByNo(int id);
}
