package pl.kubakra.flywithus.flight.reserve;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kubakra.flywithus.flight.Flight;
import pl.kubakra.flywithus.flight.TestConfiguration;
import pl.kubakra.flywithus.user.User;

import java.math.BigDecimal;
import java.util.UUID;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static pl.kubakra.flywithus.flight.TestConfiguration.NOW;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class ReservationServiceTest {

    private static final UUID ANY_UUID = UUID.randomUUID();
    private static final BigDecimal REGISTERED_USER_DISCOUNT = BigDecimal.valueOf(5);
    private static final BigDecimal NO_DISCOUNT = BigDecimal.ZERO;
    private static final BigDecimal FIFTY = BigDecimal.valueOf(50);
    private static final BigDecimal NINTY_FIVE = BigDecimal.valueOf(95);

    @Autowired
    private ReservationService reservationService;

    @Test
    public void shouldMakeReservation() {

        // given
        Flight flight = flightWithPricePerPerson(TEN);

        User user = mock(User.class);
        given(user.isRegistered()).willReturn(false);

        // when
        Reservation reservation = reservationService.reserve(flight).withPeopleCount(1).withQuickCheckIn(false).by(user);

        // then
        assertThat(reservation).isEqualTo(reservation(TEN, NO_DISCOUNT));
    }

    @Test
    public void shouldMakeReservationWithQuickCheckIn() {

        // given
        Flight flight = flightWithPricePerPerson(TEN);

        User user = mock(User.class);
        given(user.isRegistered()).willReturn(false);

        // when
        Reservation reservation = reservationService.reserve(flight)
                .withPeopleCount(2).withQuickCheckIn(true).by(user);

        // then
        assertThat(reservation).isEqualTo(reservation(BigDecimal.valueOf(120), NO_DISCOUNT));
    }

    @Test
    public void shouldMakeReservationForRegisteredUser() {

        // given
        Flight flight = flightWithPricePerPerson(BigDecimal.valueOf(100));

        User user = mock(User.class);
        given(user.isRegistered()).willReturn(true);

        // when
        Reservation reservation = reservationService.reserve(flight).withPeopleCount(1).withQuickCheckIn(false).by(user);

        // then
        assertThat(reservation).isEqualTo(reservation(NINTY_FIVE, REGISTERED_USER_DISCOUNT));
    }

    @Test
    public void shouldCalculateDiscountWithQuickCheckInPrice() {

        // given
        Flight flight = flightWithPricePerPerson(FIFTY);

        User user = mock(User.class);
        given(user.isRegistered()).willReturn(true);

        // when
        Reservation reservation = reservationService.reserve(flight).withPeopleCount(1).withQuickCheckIn(true).by(user);

        // then
        assertThat(reservation).isEqualTo(reservation(NINTY_FIVE, REGISTERED_USER_DISCOUNT));
    }

    private Reservation reservation(BigDecimal totalPrice, BigDecimal discount) {
        return new Reservation(ANY_UUID, TestConfiguration.UUID, NOW.plusDays(2),
                new Reservation.Price(totalPrice, discount));
    }

    private Flight flightWithPricePerPerson(BigDecimal pricePerPerson) {
        Flight flight = mock(Flight.class);
        given(flight.id()).willReturn(TestConfiguration.UUID);
        given(flight.pricePerPerson()).willReturn(pricePerPerson);
        return flight;
    }

}