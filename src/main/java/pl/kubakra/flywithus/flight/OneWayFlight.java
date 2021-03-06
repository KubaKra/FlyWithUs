package pl.kubakra.flywithus.flight;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

class OneWayFlight extends Flight {

    @JsonProperty
    private final Duration duration;

    OneWayFlight(UUID uuid, String company, Price price, Duration duration) {
        super(company, price, uuid);
        this.duration = duration;
    }

    @Override
    public boolean areDatesBefore(LocalDateTime time) {
        return duration.areDatesBefore(time);
    }

}