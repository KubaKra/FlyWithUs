package pl.kubakra.flywithus.payment;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public class PaymentTestFactory {

    public static Payment fakePayment() {
        return fakePayment(randomUUID());
    }

    public static Payment fakePayment(UUID reservationId) {
        return new Payment(randomUUID(), reservationId, ExternalPaymentSystemIdTestFactory.fake(), System.PAY_WITH_US);
    }

}