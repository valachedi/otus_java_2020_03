package hw09.core.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hw09.core.dao.AccountDao;
import hw09.core.model.Account;

public class DbServiceAccountImpl implements DBServiceAccount {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceAccountImpl.class);

    private final AccountDao accountDao;

    public DbServiceAccountImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public int save(Account account) {
        try (var sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();

            try {
                int id = accountDao.insertOrUpdate(account);
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
    public Optional getByNo(int no) {
        try (var sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();

            try {
                Optional<Account> result = accountDao.findByNo(no);
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
