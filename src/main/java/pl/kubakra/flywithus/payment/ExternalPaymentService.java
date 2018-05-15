package pl.kubakra.flywithus.payment;

import java.math.BigDecimal;

public interface ExternalPaymentService {

    ExternalPaymentSystemId registerNewPayment(BigDecimal value);

    boolean cancelPayment(ExternalPaymentSystemId systemId);

}