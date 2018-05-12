package pl.kubakra.flywithus.flight.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class SearchFlightsCriteria {

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

    private static class LocalDateDeserializer extends StdDeserializer<LocalDate> {

        LocalDateDeserializer() {
            super(LocalDate.class);
        }

        @Override
        public LocalDate deserialize(JsonParser jp, DeserializationContext ctx)
                throws IOException {
            return LocalDate.parse(jp.readValueAs(String.class));
        }

    }

}