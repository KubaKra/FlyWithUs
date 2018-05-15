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

    // used by jackson to deserialize
    public GetFlightsCriteria() {
    }

    GetFlightsCriteria(String from, String to, LocalDate when, LocalDate returnDate, int peopleCount) {
        this.from = from;
        this.to = to;
        this.when = when;
        this.returnDate = returnDate;
        this.peopleCount = peopleCount;
    }

    boolean isOneWayTicket() {
        return returnDate == null;
    }

    public boolean areDatesAfterThan(LocalDate now) {
        return when.isAfter(now) && (returnDate == null || (returnDate != null && returnDate.isAfter(now)));
    }

}