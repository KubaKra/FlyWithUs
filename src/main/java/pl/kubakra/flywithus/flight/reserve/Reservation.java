package pl.kubakra.flywithus.flight.reserve;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import org.springframework.hateoas.ResourceSupport;
import pl.kubakra.flywithus.tech.serialization.LocalDateTimeSerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

class Reservation extends ResourceSupport {

    private final UUID uuid;
    private final UUID flightId;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private final LocalDateTime paymentDeadline;
    @JsonProperty
    private final Price price;


    Reservation(UUID uuid, UUID flightId, LocalDateTime paymentDeadline, Price price) {
        this.uuid = uuid;
        this.flightId = flightId;
        this.paymentDeadline = paymentDeadline;
        this.price = price;
    }

    public UUID id() {
        return uuid;
    }

    public UUID flightId() {
        return flightId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (flightId != null ? !flightId.equals(that.flightId) : that.flightId != null) {
            return false;
        }
        if (paymentDeadline != null ? !paymentDeadline.equals(that.paymentDeadline) : that.paymentDeadline != null) {
            return false;
        }
        return price != null ? price.equals(that.price) : that.price == null;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (flightId != null ? flightId.hashCode() : 0);
        result = 31 * result + (paymentDeadline != null ? paymentDeadline.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uuid", uuid)
                .add("flightId", flightId)
                .add("paymentDeadline", paymentDeadline)
                .add("price", price)
                .toString();
    }

    static class Price {

        @JsonProperty
        private final BigDecimal total;
        @JsonProperty
        private final BigDecimal discount;

        Price(BigDecimal total, BigDecimal discount) {
            this.total = total;
            this.discount = discount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Price price = (Price) o;

            if (total != null ? total.compareTo(price.total) != 0 : price.total != null) return false;
            return discount != null ? discount.compareTo(price.discount) == 0 : price.discount == null;
        }

        @Override
        public int hashCode() {
            int result = total != null ? total.hashCode() : 0;
            result = 31 * result + (discount != null ? discount.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("total", total)
                    .add("discount", discount)
                    .toString();
        }

    }
}