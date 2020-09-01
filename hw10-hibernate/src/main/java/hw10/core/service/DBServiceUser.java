package hw10.core.service;

import java.util.Optional;

import hw10.core.model.User;

public interface DBServiceUser {
    long save(User user);

    Optional<User> getById(long id);
}
