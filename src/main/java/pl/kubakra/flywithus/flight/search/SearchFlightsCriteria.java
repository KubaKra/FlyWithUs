package pl.kubakra.flywithus.flight.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.kubakra.flywithus.flight.LocalDateDeserializer;

import java.time.LocalDate;

class SearchFlightsCriteria {

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

    public boolean isOneWayTicket() {
        return returnDate == null;
    }

}