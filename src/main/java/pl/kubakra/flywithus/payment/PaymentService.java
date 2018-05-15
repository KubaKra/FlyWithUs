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
        if (paymentFromRepo.get().isError()) {
            removeFromTryAgainQueue(paymentFromRepo.get().reservationId());
            return true;
        }
        return externalPaymentService.cancelPayment(paymentFromRepo.get().systemId());
    }

    public Payment registerPaymentByPayWithUs(UUID reservationId, BigDecimal total) {
        ExternalServicePaymentId systemId = registerPayment(reservationId, total);
        Payment payment = new Payment(UUID.randomUUID(), reservationId, systemId, System.PAY_WITH_US);
        paymentRepo.save(payment);
        return payment;
    }

    private ExternalServicePaymentId registerPayment(UUID reservationId, BigDecimal total) {
        try {
            return externalPaymentService.registerNewPayment(total);
        } catch (ExternalPaymentService.PaymentRegistrationFailed paymentRegistrationFailed) {
            addToTryAgainQueue(reservationId, total);
        }
        return new PaymentNotDeterminedYet();
    }

    private void addToTryAgainQueue(UUID reservationId, BigDecimal total) {
        // fake method, it'd try to send request from time to time,
        // if finally it would get response there should be MDB which get reservation by reservation id and change payment
    }

    private void removeFromTryAgainQueue(UUID reservationId) {

    }

}