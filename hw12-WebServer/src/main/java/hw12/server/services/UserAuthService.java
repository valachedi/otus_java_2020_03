package hw12.server.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
