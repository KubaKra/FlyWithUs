package pl.kubakra.flywithus.payment;

import java.util.UUID;

public final class Payment {

    private final UUID id;
    private final UUID reservationId;

    private final ExternalPaymentSystemId systemId;
    private final System system;
    private Status status;

    Payment(UUID id, UUID reservationId, ExternalPaymentSystemId systemId, System system) {
        this.id = id;
        this.reservationId = reservationId;
        this.systemId = systemId;
        this.system = system;
        status = Status.NOT_PAID;
    }

    public String link() {
        return systemId.link();
    }

    UUID id() {
        return id;
    }

    UUID reservationId() {
        return reservationId;
    }

    ExternalPaymentSystemId systemId() {
        return systemId;
    }

    void completed() {
        status = Status.PAID;
    }

    void error() {
        status = Status.ERROR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        return id != null ? id.equals(payment.id) : payment.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}