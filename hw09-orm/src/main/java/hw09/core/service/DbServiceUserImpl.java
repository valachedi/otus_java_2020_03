package hw09.core.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hw09.core.dao.UserDao;
import hw09.core.model.User;

public class DbServiceUserImpl implements DBServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

    private final UserDao userDao;

    public DbServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int save(User user) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();

            try {
                int id = userDao.insertOrUpdate(user);
                sessionManager.commitSession();
                logger.info("affected row: {}", id);
                return id;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional getById(int id) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();

            try {
                Optional<User> result = userDao.findById(id);
                logger.info("object: {}", result.orElse(null));
                return result;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }

            return Optional.empty();
        }
    }
}
