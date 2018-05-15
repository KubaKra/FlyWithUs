package pl.kubakra.flywithus.payment;

public class PaymentNotDeterminedYet implements ExternalServicePaymentId {

    @Override
    public String link() {
        return "page with additional info";
    }

    @Override
    public boolean isNotDeterminedYet() {
        return true;
    }

}