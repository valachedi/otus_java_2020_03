package hw09;

import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hw09.core.model.Account;
import hw09.core.service.DbServiceAccountImpl;
import hw09.jdbc.dao.AccountDaoJdbc;
import hw09.jdbc.DbExecutorImpl;
import hw09.jdbc.sessionmanager.SessionManagerJdbc;

public class DemoAccount {
  private static final Logger logger = LoggerFactory.getLogger(DemoAccount.class);
  private DbServiceAccountImpl dbService;
  private DataSource dataSource;

  public DemoAccount(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void run() {
    initDbService();
    Account account = createAccount();
    updateAccount(account);
  }

  private void initDbService() {
    var sessionManager = new SessionManagerJdbc(dataSource);
    DbExecutorImpl<Account> dbExecutor = new DbExecutorImpl<>();
    dbService = new DbServiceAccountImpl(new AccountDaoJdbc(sessionManager, dbExecutor));
  }

  private Account createAccount() {
    var newAccount = new Account(0, "Affiliate", 36484);
    var accountNo = dbService.save(newAccount);
    Optional<Account> createdAccount = dbService.getByNo(accountNo);

    createdAccount.ifPresentOrElse(
      crAcc -> logger.info("created account, type: {}", crAcc.getType()),
      () -> {
        logger.error("account was not created");
        throw new RuntimeException("failed to save account");
      }
    );

    return createdAccount.get();
  }

  private void updateAccount(Account account) {
    account.setRest(364);
    dbService.save(account);
    Optional<Account> updatedAccount = dbService.getByNo(account.getNo());

    updatedAccount.ifPresentOrElse(
      crUser -> logger.info("updated account, rest: {}", crUser.getRest()),
      () -> {
        logger.error("account was not updated");
        throw new RuntimeException("failed to save account");
      }
    );
  }
}
