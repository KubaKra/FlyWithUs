package pl.kubakra.flywithus.flight.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OneWayFlight.class, name = "oneWay"),
        @JsonSubTypes.Type(value = TwoWaysFlight.class, name = "twoWays")
})
public abstract class Flight {

    @JsonProperty
    private final String company;
    @JsonProperty
    private final Price price;

    public Flight(String company, Price price) {
        this.company = company;
        this.price = price;
    }

    public static class Price {

        @JsonProperty
        private final BigDecimal perPerson;
        @JsonProperty
        private final BigDecimal total;

        Price(BigDecimal perPerson, BigDecimal total) {
            this.perPerson = perPerson;
            this.total = total;
        }

    }

    public static class Duration {

        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private final LocalDateTime departure;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private final LocalDateTime arrival;

        Duration(LocalDateTime departure, LocalDateTime arrival) {
            this.departure = departure;
            this.arrival = arrival;
        }

    }

    private static class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

        LocalDateTimeSerializer() {
            super(LocalDateTime.class);
        }

        @Override
        public void serialize(LocalDateTime time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

    }

}