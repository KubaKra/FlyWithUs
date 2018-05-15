package pl.kubakra.flywithus.payment;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PaymentRepo {

    private static final Map<UUID, Payment> PAYMENTS = new ConcurrentHashMap<>();
    private static final Map<UUID, Payment> PAYMENTS_BY_RESERVATION_ID = new ConcurrentHashMap<>();
    private static final Map<ExternalPaymentSystemId, Payment> PAYMENTS_BY_SYSTEM_ID = new ConcurrentHashMap<>();

    public Optional<Payment> getBy(UUID id) {
        return Optional.ofNullable(PAYMENTS.get(id));
    }

    public void save(Payment payment) {
        PAYMENTS.put(payment.id(), payment);
        PAYMENTS_BY_RESERVATION_ID.put(payment.reservationId(), payment);
        PAYMENTS_BY_SYSTEM_ID.put(payment.systemId(), payment);
    }

    public Optional<Payment> getReservationBy(UUID reservationId) {
        return Optional.ofNullable(PAYMENTS_BY_RESERVATION_ID.get(reservationId));
    }

    public Optional<Payment> getBySystemId(ExternalPaymentSystemId systemId) {
        return Optional.ofNullable(PAYMENTS_BY_SYSTEM_ID.get(systemId));
    }
}