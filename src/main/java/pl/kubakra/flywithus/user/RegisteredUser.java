package pl.kubakra.flywithus.user;

import java.util.Optional;

class RegisteredUser implements User {

    private final String login;

    RegisteredUser(String login) {
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
