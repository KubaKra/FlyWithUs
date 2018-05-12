package pl.kubakra.flywithus.flight.search;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TwoWaysFlight extends Flight {

    @JsonProperty
    private final Duration toDuration;
    @JsonProperty
    private final Duration returnDuration;

    public TwoWaysFlight(String company, Price price, Duration toDuration, Duration returnDuration) {
        super(company, price);
        this.toDuration = toDuration;
        this.returnDuration = returnDuration;
    }


}
