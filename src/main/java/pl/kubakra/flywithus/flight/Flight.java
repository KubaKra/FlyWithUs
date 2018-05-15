package pl.kubakra.flywithus.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.ResourceSupport;
import pl.kubakra.flywithus.tech.serialization.LocalDateTimeSerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OneWayFlight.class, name = "oneWay"),
        @JsonSubTypes.Type(value = TwoWaysFlight.class, name = "twoWays")
})
public abstract class Flight extends ResourceSupport {

    private final UUID uuid;
    @JsonProperty
    private final String company;
    @JsonProperty
    private final Price price;

    Flight(String company, Price price, UUID uuid) {
        this.company = company;
        this.price = price;
        this.uuid = uuid;
    }

    public UUID id() {
        return uuid;
    }

    public BigDecimal pricePerPerson() {
        return price.perPerson;
    }

    static class Price {

        @JsonProperty
        private final BigDecimal perPerson;
        @JsonProperty
        private final BigDecimal total;

        Price(BigDecimal perPerson, BigDecimal total) {
            this.perPerson = perPerson;
            this.total = total;
        }

    }

    static class Duration {

        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private final LocalDateTime departure;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private final LocalDateTime arrival;

        Duration(LocalDateTime departure, LocalDateTime arrival) {
            this.departure = departure;
            this.arrival = arrival;
        }

    }

}