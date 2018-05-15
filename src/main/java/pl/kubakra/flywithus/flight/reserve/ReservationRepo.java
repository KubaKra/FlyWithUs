package pl.kubakra.flywithus.flight.reserve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.kubakra.flywithus.payment.Payment;
import pl.kubakra.flywithus.payment.PaymentRepo;
import pl.kubakra.flywithus.payment.PaymentService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public final class ReservationRepo {

    private static final Map<UUID, ReservationJpaEntity> RESERVATIONS = new ConcurrentHashMap<>();

    private final PaymentService paymentService;
    private final PaymentRepo paymentRepo;

    public ReservationRepo(@Autowired PaymentService paymentService, @Autowired PaymentRepo paymentRepo) {
        this.paymentService = paymentService;
        this.paymentRepo = paymentRepo;
    }

    public void save(Reservation reservation) {
        RESERVATIONS.put(reservation.id(),
                new ReservationJpaEntity(reservation.id(), reservation.flightId(), reservation.paymentDeadline(), reservation.price()));

        Optional<Payment> payment = reservation.payment();
        if (payment.isPresent()) {
            paymentRepo.save(payment.get());
        }
    }

    public Optional<Reservation> getBy(UUID id) {
        Optional<ReservationJpaEntity> data = Optional.ofNullable(RESERVATIONS.get(id));
        if (!data.isPresent()) {
            return Optional.empty();
        }
        ReservationJpaEntity reservationJpaEntity = data.get();

        Optional<Payment> payment = paymentRepo.getReservationBy(id);
        if (!payment.isPresent()) {
            // theoretically it's impossible -> for every new reservation application register new payment but:
            // it could be different microservice, for such case I'd return just Flight

            return Optional.of(new Reservation(reservationJpaEntity.id, reservationJpaEntity.flightId,
                    reservationJpaEntity.paymentDeadline, reservationJpaEntity.price));
        }

        return Optional.of(new Reservation(reservationJpaEntity.id, reservationJpaEntity.flightId,
                reservationJpaEntity.paymentDeadline, reservationJpaEntity.price, payment.get()));
    }

    public void delete(Reservation reservation) {
        if (isCancelPaymentSucceeded(reservation)) {
            RESERVATIONS.remove(reservation.id());
        }
    }

    private boolean isCancelPaymentSucceeded(Reservation reservation) {
        Optional<Payment> payment = reservation.payment();
        if (payment.isPresent()) {
            return paymentService.cancel(payment.get());
        }
        return true;
    }

    // some annotations
    private static class ReservationJpaEntity {

        private final UUID id;
        private final UUID flightId;
        private final LocalDateTime paymentDeadline;
        private final Reservation.Price price;

        private ReservationJpaEntity(UUID id, UUID flightId, LocalDateTime paymentDeadline, Reservation.Price price) {
            this.id = id;
            this.flightId = flightId;
            this.paymentDeadline = paymentDeadline;
            this.price = price;
        }
    }

}