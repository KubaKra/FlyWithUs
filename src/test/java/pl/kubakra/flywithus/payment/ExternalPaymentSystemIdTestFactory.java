package pl.kubakra.flywithus.payment;

public class ExternalPaymentSystemIdTestFactory {

    public static ExternalServicePaymentId fake() {
        return new PayWithUsId("some-fakePayment-id");
    }

}