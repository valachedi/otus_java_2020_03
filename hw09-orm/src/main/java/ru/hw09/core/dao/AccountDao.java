package hw09.core.dao;

import java.util.Optional;

import hw09.core.model.Account;
import hw09.core.sessionmanager.SessionManager;

public interface AccountDao {
    Optional<Account> findByNo(int no);
    int insert(Account account);
    int update(Account account);
    int insertOrUpdate(Account account);
    SessionManager getSessionManager();
}
