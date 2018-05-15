package pl.kubakra.flywithus.payment;

import java.util.UUID;

public final class Payment {

    private final UUID id;
    private final UUID reservationId;

    private final ExternalServicePaymentId systemId;
    private final System system;
    private Status status;

    Payment(UUID id, UUID reservationId, ExternalServicePaymentId systemId, System system) {
        this.id = id;
        this.reservationId = reservationId;
        this.systemId = systemId;
        this.system = system;
        status = systemId.isNotDeterminedYet() ? Status.ERROR : Status.NOT_PAID;
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

    ExternalServicePaymentId systemId() {
        return systemId;
    }

    void completed() {
        status = Status.PAID;
    }

    void error() {
        status = Status.ERROR;
    }

    public boolean isError() {
        return status == Status.ERROR;
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