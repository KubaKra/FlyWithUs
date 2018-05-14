package pl.kubakra.flywithus.tech.id;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdGenerator {

    public UUID generate() {
        return UUID.randomUUID();
    }

}