package pl.kubakra.flywithus.payment;

import java.math.BigDecimal;

public interface ExternalPaymentService {

    ExternalServicePaymentId registerNewPayment(BigDecimal value) throws PaymentRegistrationFailed;

    boolean cancelPayment(ExternalServicePaymentId systemId);

    class PaymentRegistrationFailed extends Exception {

    }

}