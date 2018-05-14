package pl.kubakra.flywithus.user;

import java.util.Optional;

public class RegisteredUser implements User {

    private final String login;

    public RegisteredUser(String login) {
        if (login == null) {
            throw new IllegalArgumentException("login is required");
        }
        this.login = login;
    }

    @Override
    public boolean isRegistered() {
        return true;
    }

    @Override
    public Optional<String> login() {
        return Optional.of(login);
    }

}
