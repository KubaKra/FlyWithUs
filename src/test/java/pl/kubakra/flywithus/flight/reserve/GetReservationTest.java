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

import java.util.UUID;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlyWithUsApp.class)
public class GetReservationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ReservationRepo reservationRepo;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnExistingFlight() throws Exception {

        // given
        Reservation savedReservation = new Reservation(UUID.fromString("e3e8472f-e41a-405d-a232-2348e3d6c9d4"),
                UUID.fromString("486f1894-0297-4441-9341-1e1b7edb9849"), ReservationServiceTestConfiguration.NOW.minusDays(6), new Reservation.Price(TEN, ZERO));
        reservationRepo.save(savedReservation);

        // when
        mockMvc.perform(get("/flights/reservations/e3e8472f-e41a-405d-a232-2348e3d6c9d4"))

                // then
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn404ForNotExistingReservation() throws Exception {

        // when
        mockMvc.perform(get("/flights/reservations/ee18ccca-078d-4a62-95a7-2aea11d54444"))

                // then
                .andExpect(status().isNotFound());
    }

}