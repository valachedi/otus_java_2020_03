package hw09.core.dao;

import java.util.Optional;

import hw09.core.model.User;
import hw09.core.sessionmanager.SessionManager;

public interface UserDao {
    Optional<User> findById(int id);

    int insert(User user);

    int update(User user);

    int insertOrUpdate(User user);

    SessionManager getSessionManager();
}
