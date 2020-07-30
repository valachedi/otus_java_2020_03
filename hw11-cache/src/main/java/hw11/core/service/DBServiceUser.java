package hw11.core.service;

import java.util.Optional;

import hw11.core.model.User;

public interface DBServiceUser {
    long save(User user);

    Optional<User> getById(long id);
}
