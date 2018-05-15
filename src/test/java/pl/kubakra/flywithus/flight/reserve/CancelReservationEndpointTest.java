package pl.kubakra.flywithus.flight.reserve;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import pl.kubakra.flywithus.FlyWithUsApp;
import pl.kubakra.flywithus.flight.Flight;
import pl.kubakra.flywithus.flight.FlightFactory;

import java.util.UUID;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlyWithUsApp.class)
public class CancelReservationEndpointTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ReservationRepo reservationRepo;
    @Autowired
    private FlightFactory flightFactory;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldCancelReservationWhenFlightDateIsAtLeast5DaysBeforeDeparture() throws Exception {

        // given
        Flight flight = flightFactory.create(true);

        Reservation savedReservation = new Reservation(UUID.fromString("e3e8472f-e41a-405d-a232-2348e3d6c9d4"),
                flight.id(), ReservationServiceTestConfiguration.NOW.minusDays(6), new Reservation.Price(TEN, ZERO));
        reservationRepo.save(savedReservation);

        // when
        mockMvc.perform(delete("/flights/" + flight.id().toString() + "/reservations/e3e8472f-e41a-405d-a232-2348e3d6c9d4"))

                // then
                .andExpect(status().isNoContent());
        assertThat(reservationRepo.getBy(UUID.fromString("e3e8472f-e41a-405d-a232-2348e3d6c9d4"))).isEmpty();
    }

    @Test
    public void shouldnotCancelReservationWhenFlightDateIsSoonerThan5DaysBeforeDeparture() throws Exception {

        // given
        Flight flight = flightFactory.create(false);

        Reservation savedReservation = new Reservation(UUID.fromString("e3e8472f-e41a-405d-a232-2348e3d6c9d4"),
                flight.id(), ReservationServiceTestConfiguration.NOW.minusDays(4), new Reservation.Price(TEN, ZERO));
        reservationRepo.save(savedReservation);


        // when
        mockMvc.perform(delete("/flights/" + flight.id().toString() + "/reservations/e3e8472f-e41a-405d-a232-2348e3d6c9d4"))

                // then
                .andExpect(status().isBadRequest());
        assertThat(reservationRepo.getBy(UUID.fromString("e3e8472f-e41a-405d-a232-2348e3d6c9d4"))).isNotEmpty();
    }

}