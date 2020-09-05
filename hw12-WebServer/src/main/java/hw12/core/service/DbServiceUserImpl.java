package hw12.core.service;

import hw12.core.dao.UserDao;
import hw12.core.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbServiceUserImpl implements DbServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

    private final UserDao userDao;

    public DbServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public long save(User user) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();

            try {
                userDao.insertOrUpdate(user);
                sessionManager.commitSession();
                logger.info("affected row: {}", user);
                return user.getId();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public List<User> getAll() {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            List<User> result = new ArrayList<>(1);

            try {
                result = userDao.findAll();
                logger.info("object: {}", result.size());
                return result;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }

            return result;
        }
    }

    @Override
    public Optional<User> getById(long id) {
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

    @Override
    public Optional<User> getLastUser() {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();

            try {
                Optional<User> result = userDao.findLastUser();
                logger.info("random user: {}", result.orElse(null));
                return result;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }

            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getByLogin(String login) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();

            try {
                Optional<User> result = userDao.findByLogin(login);
                logger.info("found user by login: {}", result.orElse(null));
                return result;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }

            return Optional.empty();
        }
    }
}
