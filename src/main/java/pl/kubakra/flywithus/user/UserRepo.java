package pl.kubakra.flywithus.user;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepo {

    private static final Map<String, User> USERS = ImmutableMap.of(
            "KubaKra", new RegisteredUser("KubaKra")
    );

    public User getUser(String login) {
        return USERS.getOrDefault(login, new Guest(login));
    }

}