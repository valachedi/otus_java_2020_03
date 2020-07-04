package hw09.core.service;

import java.util.Optional;
import hw09.core.model.User;

public interface DBServiceUser {
  int save(User user);
  Optional<User> getById(int id);
}
