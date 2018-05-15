package pl.kubakra.flywithus.payment;

public final class ExternalPaymentSystemId {

    private final String id;

    ExternalPaymentSystemId(String id) {
        this.id = id;
    }

    String link() {
        return "www.paywithus.com/" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExternalPaymentSystemId that = (ExternalPaymentSystemId) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}