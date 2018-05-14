package pl.kubakra.flywithus.flight.reserve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kubakra.flywithus.flight.Flight;
import pl.kubakra.flywithus.tech.id.IdGenerator;
import pl.kubakra.flywithus.tech.time.TimeService;
import pl.kubakra.flywithus.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ReservationService {

    private final TimeService timeService;
    private final IdGenerator idGenerator;
    private final ReservationRepo reservationRepo;

    public ReservationService(@Autowired TimeService timeService, @Autowired IdGenerator idGenerator,
                              @Autowired ReservationRepo reservationRepo) {
        this.timeService = timeService;
        this.idGenerator = idGenerator;
        this.reservationRepo = reservationRepo;
    }

    public ReservationDsl reserve(Flight flight) {
        return new ReservationDsl(flight);
    }

    public class ReservationDsl {

        private final Flight flight;
        private boolean quickCheckIn;

        public ReservationDsl(Flight flight) {
            this.flight = flight;
            quickCheckIn = false;
        }

        public ReservationDsl withQuickCheckIn() {
            quickCheckIn = true;
            return this;
        }


        public Reservation by(User user) {
            LocalDateTime paymentDeadline = timeService.now().plusDays(2);
            Reservation.Price price = calculatePrice(user);
            Reservation reservation = new Reservation(idGenerator.generate(), flight.id(), paymentDeadline, price);
            return reservationRepo.save(reservation);
        }

        private Reservation.Price calculatePrice(User user) {
            // It's a good place to watch in future. In case of adding new discount or price features create policy/strategy mechanism.
            // Now it'd be too much, since it's only ~10 lines of code.

            BigDecimal total = flight.totalPrice();
            if (quickCheckIn) {
                total = total.add(BigDecimal.valueOf(50)); // TODO .multiply(howManyPeople));
            }

            BigDecimal discount = BigDecimal.ZERO;
            if (user.isRegistered()) {
                discount = BigDecimal.valueOf(5);
                total = total.multiply(BigDecimal.valueOf(100).subtract(discount).divide(BigDecimal.valueOf(100)));
            }

            return new Reservation.Price(total, discount);
        }

    }

}