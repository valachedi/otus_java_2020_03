package hw11.core.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hw11.core.dao.UserDao;
import hw11.core.cache.Cache;
import hw11.core.cache.MyCache;
import hw11.core.model.User;
import hw11.core.service.DbServiceUserCacheListener;

public class DbServiceUserImpl implements DBServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

    private final Cache<Long, User> cache;
    private final UserDao userDao;

    public DbServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
        this.cache = new MyCache();
        this.cache.addListener(new DbServiceUserCacheListener<Long, User>());
    }

    @Override
    public long save(User user) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();

            try {
                userDao.insertOrUpdate(user);
                sessionManager.commitSession();
                logger.info("affected row: {}", user);
                cache.put(user.getId(), user);
                return user.getId();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional getById(long id) {
        Optional<User> userOptional;
        User user = cache.get(id);

        if(user == null) {
            userOptional = getByIdFromDb(id);

            if(userOptional.isPresent()) {
                cache.put(id, userOptional.get());
            }
        } else {
            userOptional = Optional.of(user);
        }

        return userOptional;
    }

    public Optional getByIdFromDb(long id) {
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
