package pl.kubakra.flywithus.user;

import java.util.Optional;

public interface User {

    boolean isRegistered();

    Optional<String> login();

}