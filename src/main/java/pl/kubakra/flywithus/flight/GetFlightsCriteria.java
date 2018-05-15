package pl.kubakra.flywithus.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.kubakra.flywithus.tech.serialization.LocalDateDeserializer;

import java.time.LocalDate;

public class GetFlightsCriteria {

    @JsonProperty
    private String from;
    @JsonProperty
    private String to;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate when;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate returnDate;
    @JsonProperty
    private int peopleCount;

    boolean isOneWayTicket() {
        return returnDate == null;
    }

}