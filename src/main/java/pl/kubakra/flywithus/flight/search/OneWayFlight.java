package pl.kubakra.flywithus.flight.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class OneWayFlight extends Flight {

    @JsonProperty
    private final Duration duration;

    public OneWayFlight(UUID uuid, String company, Price price, Duration duration) {
        super(company, price, uuid);
        this.duration = duration;
    }

}