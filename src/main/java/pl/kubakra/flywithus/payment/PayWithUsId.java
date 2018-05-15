package pl.kubakra.flywithus.payment;

public final class PayWithUsId implements ExternalServicePaymentId {

    private final String id;

    PayWithUsId(String id) {
        this.id = id;
    }

    @Override
    public String link() {
        return "www.paywithus.com/" + id;
    }

    @Override
    public boolean isNotDeterminedYet() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PayWithUsId that = (PayWithUsId) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}