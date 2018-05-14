package pl.kubakra.flywithus.flight.reserve;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.ResourceSupport;
import pl.kubakra.flywithus.tech.serialization.LocalDateTimeSerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

class Reservation extends ResourceSupport {

    private final UUID uuid;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private final LocalDateTime paymentDeadline;
    @JsonProperty
    private final Price price;


    Reservation(UUID uuid, LocalDateTime paymentDeadline, Price price) {
        this.uuid = uuid;
        this.paymentDeadline = paymentDeadline;
        this.price = price;
    }

    public UUID id() {
        return uuid;
    }


    static class Price {

        @JsonProperty
        private final BigDecimal total;
        @JsonProperty
        private final BigDecimal discount;

        public Price(BigDecimal total, BigDecimal discount) {
            this.total = total;
            this.discount = discount;
        }

    }

}
