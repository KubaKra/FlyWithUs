package pl.kubakra.flywithus.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

// this is naive message driven bean represenation.
// I assume such workflow:
// 1) user reserve flight,
// 2) system creates reservation and register new payment in external system,
// 3) front-end redirect to external payment web-app,
// 4) external payment web-app sends response to FlyWithUs via jms; thanks to that this communication is asynchronous
@Component
public class PayWithUsPaymentMDB {

    private final PaymentRepo paymentRepo;

    public PayWithUsPaymentMDB(@Autowired PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    //    @JmsListener(destination = "payments", containerFactory = "jmsContainerFactory")
    public void receive(PayWithUsPaymentCompleted message) {
        Optional<Payment> payment = paymentRepo.getBySystemId(message.paymentId);
        if (payment.isPresent()) {
            payment.get().completed();
        } else {
            // TODO do what business says
        }
    }

    //    @JmsListener(destination = "paymentErrors", containerFactory = "jmsContainerFactory")
    public void receive(PayWithUsPaymentError message) {
        Optional<Payment> payment = paymentRepo.getBySystemId(message.paymentId);
        if (payment.isPresent()) {
            payment.get().error();
        } else {
            // TODO do what business says
        }
    }

    public static class PayWithUsPaymentCompleted {

        private final ExternalPaymentSystemId paymentId;

        public PayWithUsPaymentCompleted(ExternalPaymentSystemId paymentId) {
            this.paymentId = paymentId;
        }

    }

    public static class PayWithUsPaymentError {

        private final ExternalPaymentSystemId paymentId;

        public PayWithUsPaymentError(ExternalPaymentSystemId paymentId) {
            this.paymentId = paymentId;
        }

    }

}