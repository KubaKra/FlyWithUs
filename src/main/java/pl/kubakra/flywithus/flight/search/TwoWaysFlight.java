package pl.kubakra.flywithus.flight.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class TwoWaysFlight extends Flight {

    @JsonProperty
    private final Duration toDuration;
    @JsonProperty
    private final Duration returnDuration;

    public TwoWaysFlight(UUID uuid, String company, Price price, Duration toDuration, Duration returnDuration) {
        super(company, price, uuid);
        this.toDuration = toDuration;
        this.returnDuration = returnDuration;
    }


}
