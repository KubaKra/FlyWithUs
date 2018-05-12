package pl.kubakra.flywithus.flight.search;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OneWayFlight extends Flight {

    @JsonProperty
    private final Duration duration;

    public OneWayFlight(String company, Price price, Duration duration) {
        super(company, price);
        this.duration = duration;
    }

}