package hw09.jdbc.dao;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hw09.core.dao.AccountDao;
import hw09.core.model.Account;
import hw09.core.sessionmanager.SessionManager;
import hw09.jdbc.DbExecutorImpl;
import hw09.jdbc.mapper.JdbcMapperImpl;
import hw09.jdbc.sessionmanager.SessionManagerJdbc;

public class AccountDaoJdbc implements AccountDao {
    private static final Logger logger = LoggerFactory.getLogger(AccountDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutorImpl<Account> dbExecutor;
    private final JdbcMapperImpl jdbcMapper;

    public AccountDaoJdbc(SessionManagerJdbc sessionManager, DbExecutorImpl<Account> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        jdbcMapper = new JdbcMapperImpl(sessionManager, dbExecutor);
    }

    @Override
    public Optional<Account> findByNo(int no) {
        return jdbcMapper.findById(no, Account.class);
    }

    @Override
    public int insertOrUpdate(Account account) {
        return jdbcMapper.insertOrUpdate(account);
    }

    @Override
    public int update(Account account) {
        return jdbcMapper.update(account);
    }

    @Override
    public int insert(Account account) {
        return jdbcMapper.insert(account);
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
