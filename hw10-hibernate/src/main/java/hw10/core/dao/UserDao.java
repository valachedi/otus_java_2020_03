package hw10.core.dao;

import java.util.Optional;

import hw10.core.model.User;
import hw10.core.sessionmanager.SessionManager;

public interface UserDao {
    Optional<User> findById(long id);

    long insert(User user);

    void update(User user);

    void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
