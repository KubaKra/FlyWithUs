package pl.kubakra.flywithus.user;

import java.util.Optional;

public class Guest implements User {

    private final String login;

    public Guest(String login) {
        this.login = login;
    }

    @Override
    public boolean isRegistered() {
        return false;
    }

    @Override
    public Optional<String> login() {
        return Optional.ofNullable(login);
    }
}
