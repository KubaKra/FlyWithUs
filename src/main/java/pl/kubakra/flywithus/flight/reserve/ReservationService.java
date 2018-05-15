package pl.kubakra.flywithus.flight.reserve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kubakra.flywithus.flight.Flight;
import pl.kubakra.flywithus.flight.reserve.Reservation.Price;
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

    public ReservationDsl_PeopleCount reserve(Flight flight) {
        return new ReservationDsl_PeopleCount(flight);
    }

    public class ReservationDsl_PeopleCount {

        private final Flight flight;

        ReservationDsl_PeopleCount(Flight flight) {
            this.flight = flight;
        }

        public ReservationDsl_QuickCheckIn withPeopleCount(int peopleCount) {
            return new ReservationDsl_QuickCheckIn(flight, peopleCount);
        }

    }

    public class ReservationDsl_QuickCheckIn {

        private final Flight flight;
        private final int peopleCount;

        ReservationDsl_QuickCheckIn(Flight flight, int peopleCount) {
            this.flight = flight;
            this.peopleCount = peopleCount;
        }

        public ReservationDsl withQuickCheckIn(boolean quickCheckIn) {
            return new ReservationDsl(flight, peopleCount, quickCheckIn);
        }

    }

    public class ReservationDsl {

        private final Flight flight;
        private final int peopleCount;
        private final boolean quickCheckIn;

        ReservationDsl(Flight flight, int peopleCount, boolean quickCheckIn) {
            this.flight = flight;
            this.peopleCount = peopleCount;
            this.quickCheckIn = quickCheckIn;
        }

        public Reservation by(User user) {
            LocalDateTime paymentDeadline = timeService.now().plusDays(2);
            Price price = calculatePrice(user);
            Reservation reservation = new Reservation(idGenerator.generate(), flight.id(), paymentDeadline, price);
            return reservationRepo.save(reservation);
        }

        private Price calculatePrice(User user) {
            // It's a good place to watch in future. In case of adding new discount or price features create policy/strategy mechanism.
            // Now it'd be too much, since it's only ~10 lines of code.

            BigDecimal total = flight.pricePerPerson().multiply(BigDecimal.valueOf(peopleCount));
            if (quickCheckIn) {
                total = total.add(BigDecimal.valueOf(50).multiply(BigDecimal.valueOf(peopleCount)));
            }

            BigDecimal discount = BigDecimal.ZERO;
            if (user.isRegistered()) {
                discount = BigDecimal.valueOf(5);
                total = total.multiply(BigDecimal.valueOf(100).subtract(discount).divide(BigDecimal.valueOf(100)));
            }

            return new Price(total, discount);
        }

    }

}