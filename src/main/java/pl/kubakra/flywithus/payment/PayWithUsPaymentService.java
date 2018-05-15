package pl.kubakra.flywithus.payment;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PayWithUsPaymentService implements ExternalPaymentService {

    private static AtomicLong id = new AtomicLong(1);

    @Override
    public ExternalPaymentSystemId registerNewPayment(BigDecimal value) {
        return new ExternalPaymentSystemId("mock-payment-via-pay-with-us-id-" + id.getAndIncrement());
    }

    @Override
    public boolean cancelPayment(ExternalPaymentSystemId systemId) {
        return true;
    }

}