package pl.kubakra.flywithus.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final ExternalPaymentService externalPaymentService;
    private final PaymentRepo paymentRepo;

    public PaymentService(@Autowired ExternalPaymentService externalPaymentService, @Autowired PaymentRepo paymentRepo) {
        this.externalPaymentService = externalPaymentService;
        this.paymentRepo = paymentRepo;
    }

    public boolean cancel(Payment payment) {
        Optional<Payment> paymentFromRepo = paymentRepo.getBy(payment.id());
        if (!paymentFromRepo.isPresent()) {
            return true;
        }
        return externalPaymentService.cancelPayment(payment.systemId());
    }

    // TODO add throw business checked exception from externalPaymentService.registerNewPayment(total);
    public Payment registerPaymentByPayWithUs(UUID reservationId, BigDecimal total) {
        ExternalPaymentSystemId systemId = externalPaymentService.registerNewPayment(total);
        Payment payment = new Payment(UUID.randomUUID(), reservationId, systemId, System.PAY_WITH_US);
        paymentRepo.save(payment);
        return payment;
    }

}