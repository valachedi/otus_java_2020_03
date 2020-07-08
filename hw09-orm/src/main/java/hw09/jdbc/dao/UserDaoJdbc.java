package hw09.jdbc.dao;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hw09.core.dao.UserDao;
import hw09.jdbc.DbExecutorImpl;
import hw09.core.model.User;
import hw09.core.sessionmanager.SessionManager;
import hw09.jdbc.sessionmanager.SessionManagerJdbc;
import hw09.jdbc.mapper.JdbcMapperImpl;

public class UserDaoJdbc implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutorImpl<User> dbExecutor;
    private final JdbcMapperImpl jdbcMapper;

    public UserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutorImpl<User> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        jdbcMapper = new JdbcMapperImpl(sessionManager, dbExecutor);
    }

    @Override
    public Optional<User> findById(int id) {
        return jdbcMapper.findById(id, User.class);
    }

    @Override
    public int insertOrUpdate(User user) {
        return jdbcMapper.insertOrUpdate(user);
    }

    @Override
    public int update(User user) {
        return jdbcMapper.update(user);
    }

    @Override
    public int insert(User user) {
        return jdbcMapper.insert(user);
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
