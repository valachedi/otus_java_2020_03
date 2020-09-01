package hw09;

import java.util.Optional;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hw09.core.model.User;
import hw09.core.service.DbServiceUserImpl;
import hw09.jdbc.dao.UserDaoJdbc;
import hw09.jdbc.DbExecutorImpl;
import hw09.jdbc.sessionmanager.SessionManagerJdbc;

public class DemoUser {
    private static final Logger logger = LoggerFactory.getLogger(DemoUser.class);
    private DbServiceUserImpl dbService;
    private DataSource dataSource;

    public DemoUser(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void run() {
        initDbService();
        User user = createUser();
        updateUser(user);
    }

    private void initDbService() {
        var sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutorImpl<User> dbExecutor = new DbExecutorImpl<>();
        dbService = new DbServiceUserImpl(new UserDaoJdbc(sessionManager, dbExecutor));
    }

    private User createUser() {
        var newUser = new User(0, "Mr. User", 31);
        var userId = dbService.save(newUser);
        Optional<User> createdUser = dbService.getById(userId);

        createdUser.ifPresentOrElse(
                crUser -> logger.info("created user, name: {}", crUser.getName()),
                () -> {
                    logger.error("user was not created");
                    throw new RuntimeException("failed to save user");
                }
        );

        return createdUser.get();
    }

    private void updateUser(User user) {
        user
                .setName("Sir Username")
                .setAge(31);

        dbService.save(user);

        Optional<User> updatedUser = dbService.getById(user.getId());

        updatedUser.ifPresentOrElse(
                crUser -> logger.info("updated user, name: {}", crUser.getName()),
                () -> {
                    logger.error("user was not created");
                    throw new RuntimeException("failed to save user");
                }
        );
    }
}
