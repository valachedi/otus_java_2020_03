package hw12.core.dao;

import java.util.Optional;

import hw12.core.model.User;
import hw12.core.sessionmanager.SessionManager;

public interface UserDao {
    Optional<User> findById(long id);
    Optional<User> findByLogin(String login);
    Optional<User> findLastUser();

    long insert(User user);
    void update(User user);
    void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
