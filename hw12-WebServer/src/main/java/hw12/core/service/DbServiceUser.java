package hw12.core.service;

import java.util.List;
import java.util.Optional;

import hw12.core.model.User;

public interface DbServiceUser {
    long save(User user);
    List<User> getAll();
    Optional<User> getById(long id);
    Optional<User> getByLogin(String login);
    Optional<User> getLastUser();
}
