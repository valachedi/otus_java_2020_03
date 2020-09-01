package hw12.server.services;

import hw12.core.service.DbServiceUser;

public class UserAuthServiceImpl implements UserAuthService {

    private final DbServiceUser dbServiceUser;

    public UserAuthServiceImpl(DbServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return dbServiceUser.getByLogin(login)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

}
