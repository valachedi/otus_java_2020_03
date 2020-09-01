package hw11.core.dao;

import java.util.Optional;

import hw11.core.model.User;
import hw11.core.sessionmanager.SessionManager;

public interface UserDao {
    Optional<User> findById(long id);

    long insert(User user);

    void update(User user);

    void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
