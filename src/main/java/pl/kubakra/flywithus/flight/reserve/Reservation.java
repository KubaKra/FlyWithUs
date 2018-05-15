package pl.kubakra.flywithus.flight.reserve;

import com.google.common.base.MoreObjects;
import jdk.nashorn.internal.ir.Node;
import pl.kubakra.flywithus.payment.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

class Reservation {

    private final UUID id;
    private final UUID flightId;
    private final LocalDateTime paymentDeadline;
    private final Price price;
    private Payment payment;

    Reservation(UUID id, UUID flightId, LocalDateTime paymentDeadline, Price price, Payment payment) {
        this.id = id;
        this.flightId = flightId;
        this.paymentDeadline = paymentDeadline;
        this.price = price;
        this.payment = payment;
    }

    UUID id() {
        return id;
    }

    UUID flightId() {
        return flightId;
    }

    Payment payment() {
        return payment;
    }

    LocalDateTime paymentDeadline() {
        return paymentDeadline;
    }

    Price price() {
        return price;
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
                .add("id", id)
                .add("flightId", flightId)
                .add("paymentDeadline", paymentDeadline)
                .add("price", price)
                .toString();
    }

    static class Price {

        private final BigDecimal total;
        private final BigDecimal discount;

        Price(BigDecimal total, BigDecimal discount) {
            this.total = total;
            this.discount = discount;
        }

        BigDecimal total() {
            return total;
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